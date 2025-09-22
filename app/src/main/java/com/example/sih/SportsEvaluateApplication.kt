package com.example.sih

import android.app.Application
import com.google.firebase.FirebaseApp

class SportsEvaluateApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // TODO: Initialize other services
        // - Initialize crash reporting
        // - Initialize analytics
        // - Initialize remote config
    }
}