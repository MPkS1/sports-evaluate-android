package com.example.sih.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih.data.models.*
import com.example.sih.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileSetupViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileSetupState())
    val uiState: StateFlow<ProfileSetupState> = _uiState.asStateFlow()

    fun updateAadhaarData(data: AadhaarData) {
        _uiState.value = _uiState.value.copy(aadhaarData = data)
    }

    fun updateHeight(height: String) {
        _uiState.value = _uiState.value.copy(height = height)
    }

    fun updateWeight(weight: String) {
        _uiState.value = _uiState.value.copy(weight = weight)
    }

    fun toggleFitnessTest(test: FitnessTest) {
        val currentTests = _uiState.value.selectedTests.toMutableSet()
        if (currentTests.contains(test)) {
            currentTests.remove(test)
        } else {
            currentTests.add(test)
        }
        _uiState.value = _uiState.value.copy(selectedTests = currentTests)
    }

    fun nextStep() {
        val currentStep = _uiState.value.currentStep
        val nextStep = when (currentStep) {
            ProfileSetupStep.Aadhaar -> ProfileSetupStep.Height
            ProfileSetupStep.Height -> ProfileSetupStep.Weight
            ProfileSetupStep.Weight -> ProfileSetupStep.FitnessTest
            ProfileSetupStep.FitnessTest -> ProfileSetupStep.Summary
            ProfileSetupStep.Summary -> ProfileSetupStep.Complete
            ProfileSetupStep.Complete -> ProfileSetupStep.Complete
        }
        _uiState.value = _uiState.value.copy(currentStep = nextStep)
    }

    fun previousStep() {
        val currentStep = _uiState.value.currentStep
        val previousStep = when (currentStep) {
            ProfileSetupStep.Aadhaar -> ProfileSetupStep.Aadhaar
            ProfileSetupStep.Height -> ProfileSetupStep.Aadhaar
            ProfileSetupStep.Weight -> ProfileSetupStep.Height
            ProfileSetupStep.FitnessTest -> ProfileSetupStep.Weight
            ProfileSetupStep.Summary -> ProfileSetupStep.FitnessTest
            ProfileSetupStep.Complete -> ProfileSetupStep.Summary
        }
        _uiState.value = _uiState.value.copy(currentStep = previousStep)
    }

    fun submitProfile(uid: String, email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val state = _uiState.value
                val profile = UserProfile(
                    fullName = state.aadhaarData.fullName,
                    age = state.aadhaarData.age.toIntOrNull() ?: 0,
                    gender = state.aadhaarData.gender,
                    address = state.aadhaarData.address,
                    aadhaarImageUri = state.aadhaarData.imageUri,
                    height = state.height.toDoubleOrNull() ?: 0.0,
                    weight = state.weight.toDoubleOrNull() ?: 0.0,
                    selectedFitnessTests = state.selectedTests.toList(),
                    fitnessResults = FitnessResults() // Initialize with default values
                )

                val result = userRepository.saveUserProfile(uid, email, profile)
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        currentStep = ProfileSetupStep.Complete,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to save profile",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Unknown error occurred",
                    isLoading = false
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}