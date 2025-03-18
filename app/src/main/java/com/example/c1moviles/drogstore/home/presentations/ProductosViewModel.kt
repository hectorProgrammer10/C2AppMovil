package com.example.c1moviles.drogstore.home.presentations

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c1moviles.drogstore.core.data.local.order.entities.OrderEntity
import com.example.c1moviles.drogstore.home.data.datasource.getProductos
import com.example.c1moviles.drogstore.home.data.datasource.postPedido
import com.example.c1moviles.drogstore.home.data.model.Pedido
import com.example.c1moviles.drogstore.home.data.model.Producto
import com.example.c1moviles.drogstore.home.domain.repository.ProductRepository
import com.example.c1moviles.drogstore.home.domain.services.TimerServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductosViewModel @Inject constructor(): ViewModel(){

    private var timerBoundService: TimerServices? = null
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TimerServices.LocalBinder
            timerBoundService = binder.getService()
            isBound = true
            startTimer() // Iniciar el temporizador cuando el servicio esté vinculado
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            timerBoundService = null
        }
    }

    //private val createOrderRepository = ProductRepository(app)

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _productos = MutableLiveData<List<Producto>?>()
    val productos: LiveData<List<Producto>?> = _productos

    private var _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private var _place = MutableLiveData<String>()
    val place: LiveData<String> = _place

    private var _cantidad = MutableLiveData<Int>()
    val cantidad: LiveData<Int> = _cantidad

    private var _precio = MutableLiveData<Int>()
    val precio:LiveData<Int> = _precio

    private var _errorGetOrders = MutableLiveData<String>()
    val errorGetOrders: LiveData<String> = _errorGetOrders

    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> = _orders

    fun onChangeNombre(nombre: String) {
        _nombre.value = nombre
    }
    fun onChangePrecio(precio: Int){
        _precio.value=precio
    }

    fun onChangePlace(place: String) {
        _place.value = place
    }

    fun onChangeCantidad(cantidad: Int) {
        _cantidad.value = cantidad
    }


    private fun startTimer() {
        timerBoundService?.startTimer(1 * 60 * 1000) { remainingTime ->
            Log.d("ProductosViewModel", "Tiempo restante: ${remainingTime / 1000} segundos")
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Limpiar recursos si es necesario
    }

    fun registerProducto(context: Context) {
        val createOrderRepository = ProductRepository(context)

        viewModelScope.launch {
            Log.d("ProductosViewModel", "Llamando a postProducto()")
            val pedido = Pedido(
                name = _nombre.value ?: "",
                place = _place.value ?: "",
                cantidad = _cantidad.value ?: 0,
                id_user = 21
            )
            val getTotal = (_cantidad.value?:0) * (_precio.value?:0)
            createOrderRepository.insertOrder(
                OrderEntity(
                    pedido = _nombre.value ?: "",
                    cantidad = _cantidad.value ?: 0,
                    total = getTotal,
                    order_created_by = 21
                )
            )
            try {
                val result = postPedido(pedido)
                if (result) {
                    _registrationStatus.value = true



                    _errorMessage.value = null
                    val intent = Intent(context, TimerServices::class.java)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    }
                    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                } else {
                    _registrationStatus.value = false
                    _errorMessage.value = "Error al obtener. Verifica los datos e inténtalo nuevamente."
                }
            } catch (e: Exception) {
                _registrationStatus.value = false
                _errorMessage.value = "Error: ${e.message}"
                Log.e("ProductosViewModel", "Error en postPedido", e)
            }
        }
    }

    fun fetchProductos() {
        viewModelScope.launch {
            try {
                _productos.value = getProductos()
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener productos: ${e.message}"
                Log.e("ProductosViewModel", "Error en fetchProductos", e)
            }
        }
    }

    fun getOrders(context: Application){
        val createOrderRepository = ProductRepository(context)
        val idUser = 21
        viewModelScope.launch {
            try{

                _orders.value = createOrderRepository.getOrders(idUser)
            }catch(e: Exception){
                _errorGetOrders.value = "Error al obtener ordenes: ${e.message}"
                Log.e("OrderViewModel", "Error en getOrders")
            }
        }
    }

    fun getLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val locationString = "Lat: ${location.latitude}, Lon: ${location.longitude}"
                    onChangePlace(locationString)
                    Log.d("ProductosViewModel", "Ubicación obtenida: $locationString")
                } else {
                    onChangePlace("Ubicación no disponible")
                    Log.d("ProductosViewModel", "La ubicación es null")
                }
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } catch (e: SecurityException) {
            onChangePlace("Permiso denegado")
            Log.e("ProductosViewModel", "Permiso de ubicación denegado", e)
        }
    }
}