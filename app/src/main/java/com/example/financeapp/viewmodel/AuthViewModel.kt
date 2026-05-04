package com.example.financeapp.viewmodel

import android.app.Application
import android.os.Message
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.data.dao.UserDao
import com.example.financeapp.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

/**
 * AuthviewModel - es un ViewModel que mandeja la autenticacion de usuarios
 */

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val UserDao = AppDatabase.getDatabase(application).userDao()
    private val _currendUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currendUser

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loanding

            try {
                val user = UserDao.getUserByemail(email)
                if (user != null && user.passwordHash == hasPassword(password)) {
                    /**LLogin Exitoso*/
                    _currendUser.value = user
                    _authState.value = AuthState.Succes
                } else {
                    _authState.value = AuthState.Error("Email o contrasena incorrecta")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error al inciar sesion: ${e.message}")
            }
        }
    }

    private fun hasPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString(" ") { "%o2x".format(it) }git
    }

    sealed class AuthState {
        object Idle : AuthState()
        object Loanding : AuthState()
        object Succes : AuthState()
        data class Error(val message: String) : AuthState()
    }

}