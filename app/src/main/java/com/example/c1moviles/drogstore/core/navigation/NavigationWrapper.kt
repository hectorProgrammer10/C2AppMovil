package com.example.c1moviles.drogstore.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.c1moviles.drogstore.home.Home
import com.example.c1moviles.drogstore.home.HomeViewModel
import com.example.c1moviles.drogstore.home.presentations.FormResource
import com.example.c1moviles.drogstore.home.presentations.ProductosViewModel
import com.example.c1moviles.drogstore.home.presentations.ViewProductos
import com.example.c1moviles.drogstore.home.presentations.ViewFarmacias
import com.example.c1moviles.drogstore.login.presentation.LoginScreen
import com.example.c1moviles.drogstore.login.presentation.LoginViewModel
import com.example.c1moviles.drogstore.register.presentation.RegisterScreen
import com.example.c1moviles.drogstore.register.presentation.RegisterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.c1moviles.drogstore.home.presentations.FarmaciasViewModel

import com.example.c1moviles.drogstore.registerStrore.data.model.Farmacia
import com.example.c1moviles.drogstore.registerStrore.presentation.RegisterStore
import com.example.c1moviles.drogstore.registerStrore.presentation.RegisterStoreViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

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
            Home(navController = navController)
        }
        composable("addProducto") {
            FormResource(productosViewModel = ProductosViewModel(), navController = navController)
        }
        composable("viewProducto") {
            ViewProductos(productosViewModel = ProductosViewModel())
        }
        composable("registerStore") {
            RegisterStore(registerStoreViewModel = RegisterStoreViewModel(), navController = navController)
        }
        composable("viewFarmacia") {
            ViewFarmacias(farmaciasViewModel = FarmaciasViewModel())
        }



    }
}