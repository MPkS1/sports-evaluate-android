package com.example.sih.data.models

data class User(
    val uid: String = "",
    val email: String = "",
    val profile: UserProfile? = null,
    val isEmailVerified: Boolean = false
)

data class UserProfile(
    val fullName: String = "",
    val age: Int = 0,
    val gender: Gender = Gender.MALE,
    val address: String = "",
    val aadhaarImageUri: String = "",
    val height: Double = 0.0, // in cm
    val weight: Double = 0.0, // in kg
    val selectedFitnessTests: List<FitnessTest> = emptyList(),
    val fitnessResults: FitnessResults? = null
)

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class FitnessTest(val displayName: String) {
    PUSH_UPS("Push-ups"),
    SIT_UPS("Sit-ups"),
    LONG_JUMP("Long Jump")
}

data class FitnessResults(
    val pushUpsCount: Int = 0,
    val sitUpsCount: Int = 0,
    val longJumpDistance: Double = 0.0, // in meters
    val testDate: Long = 0L,
    // TODO: Add AI analysis results from Python OCR and MoveNet integration
    val aiAnalysis: String = "AI analysis pending..."
)