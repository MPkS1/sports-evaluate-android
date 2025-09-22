package com.example.sih.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sih.data.models.*
import com.example.sih.ui.screens.auth.*
import com.example.sih.ui.screens.dashboard.DashboardScreen
import com.example.sih.ui.screens.profile.*
import com.example.sih.ui.viewmodel.*
import java.time.LocalDate

// Testing mode - set to false for production
const val TESTING_MODE = true

@Composable
fun SportsEvaluateNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    profileSetupViewModel: ProfileSetupViewModel = viewModel(),
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val isSignedIn by authViewModel.isSignedIn.collectAsState()
    val verificationSuccess by authViewModel.verificationSuccess.collectAsState()
    val pendingVerificationEmail by authViewModel.pendingVerificationEmail.collectAsState()
    
    val startDestination = when {
        isSignedIn && verificationSuccess -> Screen.Dashboard.route
        pendingVerificationEmail != null -> Screen.CodeVerification.createRoute(pendingVerificationEmail!!)
        isSignedIn -> Screen.ProfileSetup.route
        else -> Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Authentication Flow
        composable(Screen.Login.route) {
            val isLoading by authViewModel.isLoading.collectAsState()
            val errorMessage by authViewModel.errorMessage.collectAsState()

            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.signIn(email, password)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )

            // Navigate to dashboard when signed in
            LaunchedEffect(isSignedIn) {
                if (isSignedIn) {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }

        composable(Screen.Register.route) {
            val isLoading by authViewModel.isLoading.collectAsState()
            val errorMessage by authViewModel.errorMessage.collectAsState()

            RegisterScreen(
                onRegisterClick = { email, password ->
                    authViewModel.register(email, password)
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )

            // Navigate to code verification when email is pending
            LaunchedEffect(pendingVerificationEmail) {
                pendingVerificationEmail?.let { email ->
                    navController.navigate(Screen.CodeVerification.createRoute(email)) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            }
        }

        composable("code_verification/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val isLoading by authViewModel.isLoading.collectAsState()
            val errorMessage by authViewModel.errorMessage.collectAsState()

            CodeVerificationScreen(
                email = email,
                onVerifyClick = { code ->
                    authViewModel.verifyCode(email, code)
                },
                onResendClick = {
                    authViewModel.resendCode()
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )

            // Navigate to profile setup when verification is successful
            LaunchedEffect(verificationSuccess) {
                if (verificationSuccess) {
                    navController.navigate(Screen.ProfileSetup.route) {
                        popUpTo("code_verification/{email}") { inclusive = true }
                    }
                }
            }
        }

        // Profile Setup Flow
        composable(Screen.ProfileSetup.route) {
            ProfileSetupNavigation(
                navController = navController,
                profileSetupViewModel = profileSetupViewModel,
                authViewModel = authViewModel,
                dashboardViewModel = dashboardViewModel
            )
        }

        // Dashboard
        composable(Screen.Dashboard.route) {
            val user by dashboardViewModel.user.collectAsState()
            val currentUser = authViewModel.getCurrentUser()

            // Load user data when dashboard is accessed
            LaunchedEffect(currentUser) {
                currentUser?.let { firebaseUser ->
                    dashboardViewModel.loadUser(firebaseUser.uid)
                }
            }

            // Show dashboard when user data is available
            user?.let { userData ->
                DashboardScreen(
                    user = userData,
                    onLogoutClick = {
                        authViewModel.signOut()
                        dashboardViewModel.clearUser()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
                    },
                    onStartFitnessTest = { test ->
                        dashboardViewModel.startFitnessTest(test)
                    },
                    onClearUserData = {
                        authViewModel.forceDataReset()
                        dashboardViewModel.clearUser()
                        // Navigate back to profile setup for fresh registration
                        navController.navigate(Screen.ProfileSetup.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
                    }
                )
            } ?: run {
                // Show loading or create default user for testing
                if (TESTING_MODE) {
                    LaunchedEffect(Unit) {
                        currentUser?.let { firebaseUser ->
                            // Create a default user for testing if none exists
                            val defaultUser = User(
                                uid = firebaseUser.uid,
                                email = firebaseUser.email ?: "",
                                profile = UserProfile(
                                    fullName = "Test User",
                                    age = 25,
                                    gender = Gender.MALE,
                                    address = "Test Address",
                                    height = 175.0,
                                    weight = 70.0,
                                    selectedFitnessTests = listOf(FitnessTest.PUSH_UPS, FitnessTest.SIT_UPS)
                                ),
                                isEmailVerified = true
                            )
                            dashboardViewModel.updateCurrentUser(defaultUser)
                        }
                    }
                } else {
                    // Production: Show loading or redirect to profile setup
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileSetupNavigation(
    navController: NavHostController,
    profileSetupViewModel: ProfileSetupViewModel,
    authViewModel: AuthViewModel,
    dashboardViewModel: DashboardViewModel
) {
    val uiState by profileSetupViewModel.uiState.collectAsState()
    val currentUser = authViewModel.getCurrentUser()

    when (uiState.currentStep) {
        ProfileSetupStep.Aadhaar -> {
            AadhaarScreen(
                onBackClick = { 
                    navController.popBackStack()
                },
                onNextClick = { aadhaarNumber, fullName, age, gender, address, dateOfBirth, emergencyContact ->
                    val genderEnum = when (gender.lowercase()) {
                        "male" -> com.example.sih.data.models.Gender.MALE
                        "female" -> com.example.sih.data.models.Gender.FEMALE
                        else -> com.example.sih.data.models.Gender.OTHER
                    }
                    profileSetupViewModel.updateAadhaarData(
                        uiState.aadhaarData.copy(
                            fullName = fullName,
                            age = age,
                            gender = genderEnum,
                            address = address
                        )
                    )
                    profileSetupViewModel.nextStep()
                }
            )
        }

        ProfileSetupStep.Height -> {
            HeightScreen(
                height = uiState.height,
                onHeightChange = profileSetupViewModel::updateHeight,
                onNextClick = {
                    profileSetupViewModel.nextStep()
                },
                onBackClick = {
                    profileSetupViewModel.previousStep()
                },
                isLoading = uiState.isLoading
            )
        }

        ProfileSetupStep.Weight -> {
            WeightScreen(
                weight = uiState.weight,
                onWeightChange = profileSetupViewModel::updateWeight,
                onNextClick = {
                    profileSetupViewModel.nextStep()
                },
                onBackClick = {
                    profileSetupViewModel.previousStep()
                }
            )
        }

        ProfileSetupStep.FitnessTest -> {
            FitnessTestSelectionScreen(
                selectedTests = uiState.selectedTests,
                onTestToggle = profileSetupViewModel::toggleFitnessTest,
                onProceedClick = {
                    profileSetupViewModel.nextStep()
                },
                onBackClick = {
                    profileSetupViewModel.previousStep()
                },
                isLoading = uiState.isLoading
            )
        }

        ProfileSetupStep.Summary -> {
            SummaryScreen(
                onBackClick = {
                    profileSetupViewModel.previousStep()
                },
                onSubmitClick = {
                    currentUser?.let { user ->
                        profileSetupViewModel.submitProfile(user.uid, user.email ?: "")
                    }
                }
            )
        }

        ProfileSetupStep.Complete -> {
            // Ensure user data is set before navigating
            LaunchedEffect(Unit) {
                // Update dashboard with user data
                currentUser?.let { user ->
                    dashboardViewModel.loadUser(user.uid)
                }
                
                // Navigate to dashboard
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.ProfileSetup.route) { inclusive = true }
                }
            }
        }
    }
}