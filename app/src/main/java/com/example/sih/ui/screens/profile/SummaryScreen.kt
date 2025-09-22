package com.example.sih.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    var pushUpsEnabled by remember { mutableStateOf(false) }
    var sitUpsEnabled by remember { mutableStateOf(false) }
    var heightEnabled by remember { mutableStateOf(false) }
    var weightEnabled by remember { mutableStateOf(false) }
    var fitnessEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Summary & Controls",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Test Controls",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                TestToggle(
                    label = "Push-ups Test",
                    checked = pushUpsEnabled,
                    onCheckedChange = { pushUpsEnabled = it }
                )

                TestToggle(
                    label = "Sit-ups Test", 
                    checked = sitUpsEnabled,
                    onCheckedChange = { sitUpsEnabled = it }
                )

                TestToggle(
                    label = "Height Measurement",
                    checked = heightEnabled,
                    onCheckedChange = { heightEnabled = it }
                )

                TestToggle(
                    label = "Weight Measurement",
                    checked = weightEnabled,
                    onCheckedChange = { weightEnabled = it }
                )

                TestToggle(
                    label = "Fitness Assessment",
                    checked = fitnessEnabled,
                    onCheckedChange = { fitnessEnabled = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }

            Button(
                onClick = onSubmitClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
private fun TestToggle(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
