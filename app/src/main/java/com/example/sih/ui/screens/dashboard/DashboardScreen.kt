package com.example.sih.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sih.data.models.FitnessTest
import com.example.sih.data.models.User
import com.example.sih.data.models.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    user: User,
    onLogoutClick: () -> Unit,
    onStartFitnessTest: (FitnessTest) -> Unit,
    onClearUserData: () -> Unit = {} // Add callback for clearing data
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with logout button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sports Evaluate",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Row {
                    // Clear Data Button (for testing)
                    IconButton(
                        onClick = onClearUserData,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "🗑️ Clear All Data - Click to remove all user information",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    
                    // Logout Button
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        // Profile Information Card
        item {
            user.profile?.let { profile ->
                ProfileCard(profile = profile)
            }
        }

        // Fitness Tests Section
        item {
            Text(
                text = "Fitness Tests",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Selected Fitness Tests
        items(user.profile?.selectedFitnessTests ?: emptyList()) { test ->
            FitnessTestCard(
                test = test,
                onStartTest = { onStartFitnessTest(test) }
            )
        }

        // AI Analysis Section
        item {
            user.profile?.fitnessResults?.let { results ->
                AIAnalysisCard(aiAnalysis = results.aiAnalysis)
            }
        }

        // TODO: Integration placeholders
        item {
            IntegrationPlaceholdersCard()
        }
    }
}

@Composable
private fun ProfileCard(profile: UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Profile Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    ProfileInfoRow(label = "Name", value = profile.fullName)
                    ProfileInfoRow(label = "Age", value = "${profile.age} years")
                    ProfileInfoRow(label = "Gender", value = profile.gender.name.lowercase().replaceFirstChar { it.uppercase() })
                    ProfileInfoRow(label = "Height", value = "${profile.height} cm")
                    ProfileInfoRow(label = "Weight", value = "${profile.weight} kg")
                }
                
                // Profile Image
                if (profile.aadhaarImageUri.isNotEmpty()) {
                    Card(
                        modifier = Modifier.size(80.dp)
                    ) {
                        AsyncImage(
                            model = profile.aadhaarImageUri,
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FitnessTestCard(
    test: FitnessTest,
    onStartTest: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onStartTest
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                getTestIcon(test),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = test.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Tap to start test",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Start test",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AIAnalysisCard(aiAnalysis: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "AI Analysis",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = aiAnalysis,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun IntegrationPlaceholdersCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🚧 AI Integration Placeholders",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "TODO: Integrate Python OCR module for Aadhaar verification",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "TODO: Integrate MoveNet AI for real-time fitness test analysis",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "TODO: Add computer vision for exercise form detection",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

private fun getTestIcon(test: FitnessTest) = when (test) {
    FitnessTest.PUSH_UPS -> Icons.Default.FitnessCenter
    FitnessTest.SIT_UPS -> Icons.Default.Accessibility
    FitnessTest.LONG_JUMP -> Icons.Default.DirectionsRun
}