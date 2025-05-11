package com.yakson.sportbetapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yakson.sportbetapp.model.User
import com.yakson.sportbetapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            authRepository.login(email, password)
                .onSuccess { user ->
                    _currentUser.value = user
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Giriş başarısız"
                }

            _isLoading.value = false
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            authRepository.register(name, email, password)
                .onSuccess { user ->
                    _currentUser.value = user
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Kayıt başarısız"
                }

            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            authRepository.logout()
                .onSuccess {
                    _currentUser.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Çıkış başarısız"
                }

            _isLoading.value = false
        }
    }
} 