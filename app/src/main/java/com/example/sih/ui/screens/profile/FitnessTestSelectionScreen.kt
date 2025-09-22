package com.example.sih.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sih.data.models.FitnessTest

@Composable
fun FitnessTestSelectionScreen(
    selectedTests: Set<FitnessTest>,
    onTestToggle: (FitnessTest) -> Unit,
    onProceedClick: () -> Unit,
    onBackClick: () -> Unit,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.FitnessCenter,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Fitness Test Selection",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Select tests for analysis",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        // Fitness Tests List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(FitnessTest.values().toList()) { test ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onTestToggle(test) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedTests.contains(test),
                            onCheckedChange = { onTestToggle(test) }
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = test.displayName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Text(
                                text = getTestDescription(test),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.weight(1f),
                enabled = !isLoading
            ) {
                Text("Back")
            }
            
            Button(
                onClick = onProceedClick,
                modifier = Modifier.weight(1f),
                enabled = !isLoading && selectedTests.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Proceed")
                }
            }
        }
    }
}

private fun getTestDescription(test: FitnessTest): String {
    return when (test) {
        FitnessTest.PUSH_UPS -> "Test upper body strength and endurance"
        FitnessTest.SIT_UPS -> "Test core muscle strength and endurance"
        FitnessTest.LONG_JUMP -> "Test lower body power and coordination"
    }
}