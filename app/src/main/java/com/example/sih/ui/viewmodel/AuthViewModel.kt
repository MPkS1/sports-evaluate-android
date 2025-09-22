package com.example.sih.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih.data.models.AuthResult
import com.example.sih.data.repository.AuthRepository
import com.example.sih.di.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(
        AppContainer.provideAuthService(),
        AppContainer.provideEmailService()
    )
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isSignedIn = MutableStateFlow(authRepository.isUserSignedIn())
    val isSignedIn: StateFlow<Boolean> = _isSignedIn.asStateFlow()

    private val _pendingVerificationEmail = MutableStateFlow<String?>(null)
    val pendingVerificationEmail: StateFlow<String?> = _pendingVerificationEmail.asStateFlow()

    private val _verificationSuccess = MutableStateFlow(false)
    val verificationSuccess: StateFlow<Boolean> = _verificationSuccess.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = authRepository.signIn(email, password)) {
                is AuthResult.Success -> {
                    _isSignedIn.value = true
                }
                is AuthResult.Error -> {
                    _errorMessage.value = result.message
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = authRepository.register(email, password)) {
                is AuthResult.Success -> {
                    _pendingVerificationEmail.value = email
                }
                is AuthResult.Error -> {
                    _errorMessage.value = result.message
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    fun verifyCode(email: String, code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val isValid = authRepository.verifyCode(email, code)
            if (isValid) {
                _verificationSuccess.value = true
                _pendingVerificationEmail.value = null
            } else {
                _errorMessage.value = "Invalid or expired code"
            }

            _isLoading.value = false
        }
    }

    fun resendCode() {
        val email = _pendingVerificationEmail.value ?: return
        
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = authRepository.resendCode(email)) {
                is AuthResult.Error -> {
                    _errorMessage.value = result.message
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    fun signOut() {
        authRepository.signOut()
        _isSignedIn.value = false
        _verificationSuccess.value = false
        _pendingVerificationEmail.value = null
        _errorMessage.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun getCurrentUser() = authRepository.getCurrentUser()
}