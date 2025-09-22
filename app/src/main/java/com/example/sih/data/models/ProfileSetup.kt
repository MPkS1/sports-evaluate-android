package com.example.sih.data.models

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Loading : AuthResult()
}

sealed class ProfileSetupStep {
    object Aadhaar : ProfileSetupStep()
    object Height : ProfileSetupStep()
    object Weight : ProfileSetupStep()
    object FitnessTest : ProfileSetupStep()
    object Summary : ProfileSetupStep()
    object Complete : ProfileSetupStep()
}

data class ProfileSetupState(
    val currentStep: ProfileSetupStep = ProfileSetupStep.Aadhaar,
    val aadhaarData: AadhaarData = AadhaarData(),
    val height: String = "",
    val weight: String = "",
    val selectedTests: Set<FitnessTest> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class AadhaarData(
    val fullName: String = "",
    val age: String = "",
    val gender: Gender = Gender.MALE,
    val address: String = "",
    val imageUri: String = ""
)