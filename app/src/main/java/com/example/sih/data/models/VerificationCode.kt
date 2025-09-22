package com.example.sih.data.models

data class VerificationCode(
    val code: String,
    val email: String,
    val expirationTime: Long,
    val isUsed: Boolean = false
) {
    fun isExpired(): Boolean = System.currentTimeMillis() > expirationTime
    
    companion object {
        const val EXPIRATION_DURATION_MS = 60_000L // 1 minute
        
        fun generate(email: String): VerificationCode {
            val code = (100000..999999).random().toString()
            val expirationTime = System.currentTimeMillis() + EXPIRATION_DURATION_MS
            return VerificationCode(code, email, expirationTime)
        }
    }
}