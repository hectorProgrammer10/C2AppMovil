package com.example.c1moviles.drogstore.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.c1moviles.drogstore.home.Home

import com.example.c1moviles.drogstore.home.presentations.FormResource
import com.example.c1moviles.drogstore.home.presentations.ProductosViewModel
import com.example.c1moviles.drogstore.home.presentations.ViewProductos

import com.example.c1moviles.drogstore.login.presentation.LoginScreen
import com.example.c1moviles.drogstore.login.presentation.LoginViewModel
import com.example.c1moviles.drogstore.register.presentation.RegisterScreen
import com.example.c1moviles.drogstore.register.presentation.RegisterViewModel


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel = LoginViewModel(), navController = navController)
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                registerViewModel = RegisterViewModel()
            )
        }
        composable("home") {
            Home(navController = navController, productosViewModel = ProductosViewModel(context))
        }
        composable("addProducto/{nombre}/{precio}") {backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getInt("precio")?: 0
            FormResource(productosViewModel = ProductosViewModel(context), navController = navController, nombre=nombre,precio =precio)
        }
        composable("viewProducto") {
            ViewProductos(productosViewModel = ProductosViewModel(context), navController = navController)
        }




    }
}