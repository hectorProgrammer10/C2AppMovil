package com.example.c1moviles.drogstore.home.presentations
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c1moviles.drogstore.home.data.datasource.getProductos
import com.example.c1moviles.drogstore.home.data.datasource.postProducto
import com.example.c1moviles.drogstore.register.data.datasource.postUser
import com.example.c1moviles.drogstore.home.data.model.Producto
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

    private var _precio = MutableLiveData<Float>()
    val precio : LiveData<Float> = _precio

    private var _cantidad = MutableLiveData<Int>()
    val cantidad : LiveData<Int> = _cantidad

    private var _receta = MutableLiveData<String>()
    val receta : LiveData<String> = _receta
    /////////////////////////////////////
    fun onChangeNombre(nombre : String) {
        _nombre.value = nombre
    }
    fun onChangePrecio(precio : Float) {
        _precio.value = precio
    }
    fun onChangeCantidad(cantidad : Int) {
        _cantidad.value = cantidad
    }
    fun onChangeReceta(receta : String) {
        _receta.value = receta
    }

    fun registerProducto() {
        viewModelScope.launch {
            Log.d("ProductosViewModel", "Llamando a postProducto()")
            val producto = Producto(
                nombre = _nombre.value ?: "",
                precio = _precio.value ?: 0f,
                cantidad = _cantidad.value ?: 0,
                receta = _receta.value ?: ""
            )
            val result = postProducto(producto)

            if (result) {
                _registrationStatus.value = true
                _errorMessage.value = null
            } else {
                _registrationStatus.value = false
                _errorMessage.value = "Error al registrar. Verifica los datos e inténtalo nuevamente."
            }

            Log.d("ProductosViewModel", "Resultado de postProducto(): $result")
        }
    }

    fun fetchProductos() {
        viewModelScope.launch {
            _productos.value = getProductos()
        }
    }

}