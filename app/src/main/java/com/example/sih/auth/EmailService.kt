package com.example.sih.auth

import android.util.Log
import com.example.sih.data.models.VerificationCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService {
    
    // TODO: Replace with your actual email service credentials
    // For production, use environment variables or secure storage
    private val senderEmail = "your-app-email@gmail.com"
    private val senderPassword = "your-app-password"
    
    private val verificationCodes = mutableMapOf<String, VerificationCode>()
    
    suspend fun sendVerificationCode(email: String): Result<VerificationCode> {
        return withContext(Dispatchers.IO) {
            try {
                val code = VerificationCode.generate(email)
                
                // Store the code for verification
                verificationCodes[email] = code
                
                // Send email (commented out for demo - requires actual email setup)
                // sendEmail(email, code.code)
                
                // For demo purposes, log the code
                Log.d("EmailService", "Verification code for $email: ${code.code}")
                
                Result.success(code)
            } catch (e: Exception) {
                Log.e("EmailService", "Failed to send verification code", e)
                Result.failure(e)
            }
        }
    }
    
    fun verifyCode(email: String, inputCode: String): Boolean {
        val storedCode = verificationCodes[email] ?: return false
        
        return if (storedCode.isExpired() || storedCode.isUsed) {
            verificationCodes.remove(email)
            false
        } else if (storedCode.code == inputCode) {
            verificationCodes[email] = storedCode.copy(isUsed = true)
            true
        } else {
            false
        }
    }
    
    fun resendVerificationCode(email: String): Result<VerificationCode> {
        verificationCodes.remove(email) // Remove old code
        return runCatching {
            val code = VerificationCode.generate(email)
            verificationCodes[email] = code
            
            // For demo purposes, log the code
            Log.d("EmailService", "New verification code for $email: ${code.code}")
            
            code
        }
    }
    
    // TODO: Implement actual email sending
    // This is a placeholder implementation
    private fun sendEmail(recipientEmail: String, verificationCode: String) {
        try {
            val props = Properties().apply {
                put("mail.smtp.host", "smtp.gmail.com")
                put("mail.smtp.port", "587")
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
            }
            
            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(senderEmail, senderPassword)
                }
            })
            
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(senderEmail))
                addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
                subject = "Sports Evaluate - Verification Code"
                setText("""
                    Welcome to Sports Evaluate!
                    
                    Your verification code is: $verificationCode
                    
                    This code will expire in 1 minute.
                    
                    If you didn't request this code, please ignore this email.
                """.trimIndent())
            }
            
            Transport.send(message)
            Log.d("EmailService", "Verification email sent to $recipientEmail")
            
        } catch (e: Exception) {
            Log.e("EmailService", "Failed to send email", e)
            throw e
        }
    }
}