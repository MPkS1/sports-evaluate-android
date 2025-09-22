package com.example.sih.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object CodeVerification : Screen("code_verification/{email}") {
        fun createRoute(email: String) = "code_verification/$email"
    }
    object ProfileSetup : Screen("profile_setup")
    object Dashboard : Screen("dashboard")
}

// Profile Setup sub-screens
sealed class ProfileSetupScreen(val route: String) {
    object Aadhaar : ProfileSetupScreen("profile_setup/aadhaar")
    object Height : ProfileSetupScreen("profile_setup/height")
    object Weight : ProfileSetupScreen("profile_setup/weight")
    object FitnessTest : ProfileSetupScreen("profile_setup/fitness_test")
    object Summary : ProfileSetupScreen("profile_setup/summary")
}