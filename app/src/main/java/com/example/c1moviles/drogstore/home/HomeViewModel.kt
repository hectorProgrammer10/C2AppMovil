package com.example.c1moviles.drogstore.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel() : ViewModel() {
    private var _showForm = MutableLiveData<Boolean>()
    val showForm : LiveData<Boolean> = _showForm

    fun onChangeForm(shoeForm : Boolean) {
        _showForm.value = shoeForm
    }


}