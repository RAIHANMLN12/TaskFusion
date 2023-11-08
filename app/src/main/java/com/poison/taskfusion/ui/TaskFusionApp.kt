package com.poison.taskfusion.ui

import android.annotation.SuppressLint
import android.preference.PreferenceManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskFusionApp() {
    val navController = rememberNavController()
    val items = listOf("home", "calendar", "profile")
    var selectedItem by remember { mutableStateOf(0) }
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    val isFirstInstall = sharedPreferences.getBoolean("isFirstInstall", true)

    NavHost(
        navController = navController,
        startDestination = if (isFirstInstall) "auth" else "home"
    ) {
        composable("auth") {
            if (isFirstInstall) {
                AuthorizationScreen {
                    sharedPreferences.edit().putBoolean("isFirstInstall", false).apply()
                    navController.navigate("home")
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("home")
                }
            }
        }
        composable("home") {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            val icon = when (index) {
                                0 -> Icons.Filled.Home
                                1 -> Icons.Filled.CalendarMonth
                                2 -> Icons.Filled.AccountCircle
                                else -> Icons.Filled.Favorite // Default icon
                            }
                            NavigationBarItem(
                                icon = { Icon(icon, contentDescription = null) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    navigateToScreen(index, navController)
                                }
                            )
                        }
                    }
                }
            ) {
                HomeScreen()
            }
        }
        composable("calendar") {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            val icon = when (index) {
                                0 -> Icons.Filled.Home
                                1 -> Icons.Filled.CalendarMonth
                                2 -> Icons.Filled.AccountCircle
                                else -> Icons.Filled.Favorite // Default icon
                            }
                            NavigationBarItem(
                                icon = { Icon(icon, contentDescription = null) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    navigateToScreen(index, navController)
                                }
                            )
                        }
                    }
                }
            ) {
                CalendarScreen()
            }
        }
        composable("profile") {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            val icon = when (index) {
                                0 -> Icons.Filled.Home
                                1 -> Icons.Filled.CalendarMonth
                                2 -> Icons.Filled.AccountCircle
                                else -> Icons.Filled.Favorite // Default icon
                            }
                            NavigationBarItem(
                                icon = { Icon(icon, contentDescription = null) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    navigateToScreen(index, navController)
                                }
                            )
                        }
                    }
                }
            ) {
                ProfileScreen()
            }
        }
    }
}

fun navigateToScreen(index: Int, navController: NavHostController) {
    val screen = when (index) {
        0 -> "home"
        1 -> "calendar"
        2 -> "profile"
        else -> "home"
    }
    navController.navigate(screen)
}

