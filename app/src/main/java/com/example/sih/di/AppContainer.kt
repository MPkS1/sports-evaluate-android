package com.example.sih.di

import com.example.sih.auth.AuthService
import com.example.sih.auth.EmailService
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Singleton

// Simple manual dependency injection for demo
// In a production app, you would use Dagger Hilt or similar

object AppContainer {
    
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    
    @Singleton
    fun provideAuthService(): AuthService = AuthService(provideFirebaseAuth())
    
    @Singleton
    fun provideEmailService(): EmailService = EmailService()
}