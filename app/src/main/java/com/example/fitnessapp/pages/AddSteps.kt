package com.example.fitnessapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.ui.theme.Raleway

@Composable
fun AddSteps(navController : NavController) {
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Add Steps", style = TextStyle(
                    fontFamily = Raleway,
                    fontSize = 20.sp
                )
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Screen.HomePage.route)
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(Icons.Filled.MoreVert, null)
                }
            })
        }
}


