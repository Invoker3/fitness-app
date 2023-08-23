package com.example.fitnessapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.Goal
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway

@Composable
fun EditHistory(navController:NavController,db: RoomDB,args:String, homePageViewModel: HomePageViewModel
) {
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Edit Activity History", style = TextStyle(
                    fontFamily = Raleway,
                    fontSize = 20.sp
                ))
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Screen.History.route)
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            actions = {
                IconButton(onClick = {
                    navController.navigate(Screen.UserPreferences.route)
                }) {
                    Icon(Icons.Filled.Settings, null)
                }
            })
        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(350.dp),
            backgroundColor = Color(0xFFD1D1D1)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var goal: Goal = dropDownMenuGoal(db)
                var steps by remember {
                    mutableStateOf(
                        TextFieldValue(
                            homePageViewModel.getTotalActivityStepsByID(db, args.toInt()).toString()
                        )
                    )
                }
                OutlinedTextField(
                    value = steps,
                    singleLine = true,
                    label = { Text(text = "Change Steps Achieved") },
                    onValueChange = {
                        steps = it
                    }
                )
                Button(
                    onClick = {
                        if(homePageViewModel.getTotalActivityStepsByID(db, args.toInt()) != steps.text.toInt())
                            homePageViewModel.updateActivityHistory(db, args.toInt(), steps.text.toInt(), goal.goalId)
                        navController.navigate(Screen.History.route)
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Edit")
                }
            }
        }
        BottomBar(navController)
    }
}