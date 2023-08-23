package com.example.fitnessapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.fitnessapp.Screen

@Composable
fun BottomBar(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MyBottomBar(navController) },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){}
    }
}

@Composable
fun MyBottomBar(navController: NavController) {
    val bottomMenuItemsList = prepareBottomMenu()
    var selectedItem  by remember { mutableStateOf("") }

    BottomAppBar(
        cutoutShape = CircleShape
    ) {
        bottomMenuItemsList.forEach() { menuItem ->
            BottomNavigationItem(
                selected = (selectedItem == menuItem.label),
                label ={Text(menuItem.label)},
                onClick = {
                    selectedItem = menuItem.label
                    if(selectedItem == "Goals"){
                        navController.navigate(Screen.Goals.route)
                    }
                    else if (selectedItem == "Home") {
                        navController.navigate(Screen.HomePage.route)
                    }
                    else
                    {
                        navController.navigate(Screen.History.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = menuItem.icon,
                        contentDescription = menuItem.label
                    )
                },
                alwaysShowLabel = true,
                enabled = true
            )
        }
    }
}

private fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    bottomMenuItemsList.add(BottomMenuItem(label = "Goals", icon = Icons.Outlined.Flag))
    bottomMenuItemsList.add(BottomMenuItem(label = "Home", icon = Icons.Outlined.Home))
    bottomMenuItemsList.add(BottomMenuItem(label = "History", icon = Icons.Outlined.History ))

    return bottomMenuItemsList
}

data class BottomMenuItem(val label: String, val icon: ImageVector)
