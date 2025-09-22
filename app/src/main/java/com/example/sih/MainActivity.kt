package com.example.sih

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sih.navigation.SportsEvaluateNavigation
import com.example.sih.ui.theme.SihTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SihTheme {
                SportsEvaluateApp()
            }
        }
    }
}

@Composable
fun SportsEvaluateApp() {
    val navController = rememberNavController()
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        SportsEvaluateNavigation(
            navController = navController
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SportsEvaluateAppPreview() {
    SihTheme {
        SportsEvaluateApp()
    }
}