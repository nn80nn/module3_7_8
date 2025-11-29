package com.example.module3_7_8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.module3_7_8.navigation.NavGraph
import com.example.module3_7_8.presentation.viewmodel.TodoViewModel
import com.example.module3_7_8.presentation.viewmodel.TodoViewModelFactory
import com.example.module3_7_8.ui.theme.Module3_7_8Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module3_7_8Theme {
                val navController = rememberNavController()
                val viewModel: TodoViewModel = viewModel(
                    factory = TodoViewModelFactory(applicationContext)
                )

                NavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}