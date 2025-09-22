package com.example.sih.data.repository

import com.example.sih.auth.AuthService
import com.example.sih.auth.EmailService
import com.example.sih.data.models.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository(
    private val authService: AuthService,
    private val emailService: EmailService
) {
    private val _authState = MutableStateFlow<AuthResult>(AuthResult.Success)
    val authState: StateFlow<AuthResult> = _authState.asStateFlow()
    
    private val _currentUserEmail = MutableStateFlow<String?>(null)
    val currentUserEmail: StateFlow<String?> = _currentUserEmail.asStateFlow()

    init {
        _currentUserEmail.value = authService.currentUser?.email
    }

    suspend fun signIn(email: String, password: String): AuthResult {
        _authState.value = AuthResult.Loading
        
        return try {
            val result = authService.signInWithEmailAndPassword(email, password)
            if (result.isSuccess) {
                _currentUserEmail.value = result.getOrNull()?.email
                _authState.value = AuthResult.Success
                AuthResult.Success
            } else {
                val error = AuthResult.Error(result.exceptionOrNull()?.message ?: "Sign in failed")
                _authState.value = error
                error
            }
        } catch (e: Exception) {
            val error = AuthResult.Error(e.message ?: "Sign in failed")
            _authState.value = error
            error
        }
    }

    suspend fun register(email: String, password: String): AuthResult {
        _authState.value = AuthResult.Loading
        
        return try {
            val result = authService.createUserWithEmailAndPassword(email, password)
            if (result.isSuccess) {
                _currentUserEmail.value = result.getOrNull()?.email
                
                // Send verification code
                val codeResult = emailService.sendVerificationCode(email)
                if (codeResult.isSuccess) {
                    _authState.value = AuthResult.Success
                    AuthResult.Success
                } else {
                    val error = AuthResult.Error("Failed to send verification code")
                    _authState.value = error
                    error
                }
            } else {
                val error = AuthResult.Error(result.exceptionOrNull()?.message ?: "Registration failed")
                _authState.value = error
                error
            }
        } catch (e: Exception) {
            val error = AuthResult.Error(e.message ?: "Registration failed")
            _authState.value = error
            error
        }
    }

    suspend fun verifyCode(email: String, code: String): Boolean {
        return emailService.verifyCode(email, code)
    }

    suspend fun resendCode(email: String): AuthResult {
        return try {
            val result = emailService.resendVerificationCode(email)
            if (result.isSuccess) {
                AuthResult.Success
            } else {
                AuthResult.Error("Failed to resend code")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to resend code")
        }
    }

    fun signOut() {
        authService.signOut()
        _currentUserEmail.value = null
        _authState.value = AuthResult.Success
    }

    fun isUserSignedIn(): Boolean = authService.isUserSignedIn()
    
    fun getCurrentUser(): FirebaseUser? = authService.currentUser
}