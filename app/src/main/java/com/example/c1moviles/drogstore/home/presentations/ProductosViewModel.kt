package com.example.c1moviles.drogstore.home.presentations
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c1moviles.drogstore.home.data.datasource.getProductos
import com.example.c1moviles.drogstore.home.data.datasource.postPedido
import com.example.c1moviles.drogstore.register.data.datasource.postUser
import com.example.c1moviles.drogstore.home.data.model.Producto
import com.example.c1moviles.drogstore.home.data.model.Pedido
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductosViewModel @Inject constructor() : ViewModel() {
    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage


    private val _productos = MutableLiveData<List<Producto>?>()
    val productos: LiveData<List<Producto>?> = _productos

    private var _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private var _place = MutableLiveData<String>()
    val place : LiveData<String> = _place


    private var _cantidad = MutableLiveData<Int>()
    val cantidad : LiveData<Int> = _cantidad
    /////////////////////////////////////
    fun onChangeNombre(nombre : String) {
        _nombre.value = nombre
    }
    fun onChangePlace(place : String) {
        _place.value = place
    }

    fun onChangeCantidad(cantidad : Int) {
        _cantidad.value = cantidad
    }

    fun registerProducto() {
        viewModelScope.launch {
            Log.d("ProductosViewModel", "Llamando a postProducto()")
            val pedido = Pedido(
                name = _nombre.value ?: "",
                place = _place.value ?: "",
                cantidad = _cantidad.value ?: 0,
                id_user = 21
            )
            val result = postPedido(pedido)

            if (result) {
                _registrationStatus.value = true
                _errorMessage.value = null
            } else {
                _registrationStatus.value = false
                _errorMessage.value = "Error al obtener. Verifica los datos e inténtalo nuevamente."
            }

            Log.d("ProductosViewModel", "Resultado de postPedido(): $result")
        }
    }

    fun fetchProductos() {
        viewModelScope.launch {
            _productos.value = getProductos()
        }
    }

    // Función para obtener la ubicación actual y actualizar _place
    fun getLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Configura la solicitud de ubicación
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
                    Log.d("RegisterStoreViewModel", "Ubicación obtenida: $locationString")
                } else {
                    onChangePlace("Ubicación no disponible")
                    Log.d("RegisterStoreViewModel", "La ubicación es null")
                }
                // Una vez obtenida la ubicación, eliminamos las actualizaciones para evitar callbacks innecesarios
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } catch (e: SecurityException) {
            onChangePlace("Permiso denegado")
            Log.e("RegisterStoreViewModel", "Permiso de ubicación denegado", e)
        }

    }

}