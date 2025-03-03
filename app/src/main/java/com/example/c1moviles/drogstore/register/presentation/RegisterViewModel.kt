package com.example.c1moviles.drogstore.register.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c1moviles.drogstore.register.data.datasource.postUser
import com.example.c1moviles.drogstore.register.data.model.User
import com.example.c1moviles.drogstore.register.data.model.UserRequest

import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    // LiveData para los datos del formulario
    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _nombre = MutableLiveData("")
    val nombre: LiveData<String> = _nombre

    // LiveData para el estado de registro
    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus

    // LiveData para almacenar mensajes de error
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Métodos para actualizar cada campo
    fun onChangeUsername(username: String) { _username.value = username }
    fun onChangePassword(password: String) { _password.value = password }
    fun onChangeEmail(email: String) { _email.value = email }
    fun onChangeNombre(nombre: String) { _nombre.value = nombre }

    // Llama a la función postUser() pasando los datos del formulario
    fun registerUser() {
        viewModelScope.launch {
            Log.d("RegisterViewModel", "Llamando a postUser()")
            val user = User(
                username = _username.value ?: "",
                password = _password.value ?: "",
                email = _email.value ?: "",
                nombre = _nombre.value ?: ""
            )
            val result = postUser(user)

            if (result) {
                _registrationStatus.value = true
                _errorMessage.value = null  // Limpia cualquier error anterior
            } else {
                _registrationStatus.value = false
                _errorMessage.value = "Error al registrar. Verifica los datos e inténtalo nuevamente."
            }

            Log.d("RegisterViewModel", "Resultado de postUser(): $result")
        }
    }
}


