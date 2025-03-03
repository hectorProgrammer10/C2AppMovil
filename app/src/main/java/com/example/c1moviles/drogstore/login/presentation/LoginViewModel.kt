package com.example.c1moviles.drogstore.login.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c1moviles.drogstore.login.data.datasource.postUser
import com.example.c1moviles.drogstore.login.data.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage


    private var _username = MutableLiveData<String>()
    val username : LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password : LiveData<String> = _password


    fun onChangeUsername(username : String) {
        _username.value = username
    }

    fun onChangePassword (password : String) {
        _password.value = password
    }

    fun loginUser() {
        viewModelScope.launch {
            Log.d("LoginViewModel", "Llamando a postUser()")
            val user = User(
                username = _username.value ?: "",
                password = _password.value ?: ""
            )
            val result = postUser(user)

            if (result) {
                _registrationStatus.value = true
                _errorMessage.value = null  // Limpia cualquier error anterior
            } else {
                _registrationStatus.value = false
                _errorMessage.value = "Error al registrar. Verifica los datos e inténtalo nuevamente."
            }

            Log.d("LoginViewModel", "Resultado de postUser(): $result")
        }
    }
}