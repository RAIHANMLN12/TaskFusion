package com.poison.taskfusion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poison.taskfusion.R
import com.poison.taskfusion.viewModel.UserViewModel

@Composable
fun ProfileScreen(){
    val userViewModel = UserViewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "profileScreen"){
        composable("profileScreen"){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                HeaderProfileScreen(fullName = userViewModel.getUserFullName())
                Spacer(modifier = Modifier.height(35.dp))
                AccountInformation {
                    navController.navigate("accountInformationScreen")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Setting {
                    navController.navigate("settingScreen")
                }
            }
        }
        composable("accountInformationScreen"){
            AccountInformationScreen {
                navController.navigate("profileScreen")
            }
        }
        composable("settingScreen"){
            SettingScreen {
                navController.navigate("profileScreen")
            }
        }
    }
}

@Composable
fun HeaderProfileScreen(
    fullName: String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.user1),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = fullName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF000000)
        )
    }
}

@Composable
fun AccountInformation(
    navigateToDetail: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Person, 
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Account Information",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF000000)
            )
        }
        IconButton(
            onClick = {
                navigateToDetail()
            }
        ) {
            Icon(
                Icons.Rounded.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp),
                tint = Color(0xFF14213D)
            )
        }
    }
}

@Composable
fun Setting(
    navigateToSettingScreen: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Settings,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color(0xFF14213D)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF14213D)
            )
        }
        IconButton(
            onClick = {
                navigateToSettingScreen()
            }
        ) {
            Icon(
                Icons.Rounded.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp),
                tint = Color(0xFF14213D)
            )
        }
    }
}

@Composable
fun AccountInformationScreen(
    backToPreviousScreen: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "information screen",
            color = Color(0xFF000000)
        )
    }
}

@Composable
fun SettingScreen(
    backToPreviousScreen: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Setting Screen",
            color = Color(0xFF000000)
        )
    }
}



@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}


