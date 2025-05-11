package com.yakson.sportbetapp.model

sealed class AuthState {
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
    object Loading : AuthState()
    object Initial : AuthState()
} 