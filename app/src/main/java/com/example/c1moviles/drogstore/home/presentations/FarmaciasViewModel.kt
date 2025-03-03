package com.example.c1moviles.drogstore.home.presentations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c1moviles.drogstore.home.data.datasource.getFarmacias
import com.example.c1moviles.drogstore.home.data.datasource.getProductos
import com.example.c1moviles.drogstore.home.data.model.Producto
import com.example.c1moviles.drogstore.registerStrore.data.model.Farmacia
import kotlinx.coroutines.launch
import javax.inject.Inject

class FarmaciasViewModel @Inject constructor() : ViewModel()  {
    private val _farmacias = MutableLiveData<List<Farmacia>?>()
    val farmacias: LiveData<List<Farmacia>?> = _farmacias

    fun fetchProductos() {
        viewModelScope.launch {
            _farmacias.value = getFarmacias()
        }
    }
}