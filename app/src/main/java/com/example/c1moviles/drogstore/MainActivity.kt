package com.example.c1moviles.drogstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.c1moviles.drogstore.core.navigation.AppNavigator
import com.example.c1moviles.ui.theme.C1movilesTheme

import dagger.hilt.android.AndroidEntryPoint


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppNavigator()
        }
    }
}
