package com.example.fitnessapp.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.Activity
import com.example.fitnessapp.database.Goal
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.HistoryPageViewModel
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHistory(navController : NavController, db: RoomDB, homePageViewModel: HomePageViewModel,historyPageViewModel: HistoryPageViewModel) {
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Add Activity History", style = TextStyle(
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
                .height(500.dp),
            backgroundColor = Color(0xFFD1D1D1)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var date by remember { mutableStateOf(TextFieldValue("")) }
                var dateCheck by remember { mutableStateOf(false) }
                androidx.compose.material3.OutlinedTextField(
                    modifier = Modifier.padding(20.dp),
                    singleLine = true,
                    placeholder = {
                        Text("DD-MM-YYYY")
                    },

                    value = date,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if(historyPageViewModel.dateCheck(db, date.text) != 0)
                                dateCheck = true
                        }),
                    label = { Text(text = "Date of Activity") },
                    onValueChange = {
                        date = it
                    },
                    supportingText = {
                        if(dateCheck) {
                            Text("Date Already Exist")
                        }
                    },
                    isError = dateCheck
                )
                var goal: Goal = dropDownMenuGoal(db)
                var steps by remember { mutableStateOf(TextFieldValue("")) }
                lateinit var activity: Activity
                androidx.compose.material3.OutlinedTextField(
                    modifier = Modifier.padding(20.dp),
                    value = steps,
                    singleLine = true,
                    label = { Text(text = "Steps Achieved") },
                    onValueChange = {
                        steps = it
                    }
                )
                Button(onClick = {
                    activity.goalIdActivity = goal.goalId
                    activity.stepsAchieved = steps.text.toInt()
                    activity.date = date.text
                    activity.historyRecording = "Y"
                    homePageViewModel.insertActivity(db, activity)
                    navController.navigate(Screen.History.route) },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Add History", style = TextStyle(
                        fontFamily = Raleway,
                        fontSize = 15.sp
                    ))
                }
            }
        }
        BottomBar(navController)
    }
}

@Composable
fun dropDownMenuGoal(db: RoomDB): Goal {

    var expanded by remember { mutableStateOf(false) }
    val suggestions = db.goalDao().findAllGoals()
    val selectCategory by rememberSaveable { mutableStateOf(suggestions) }
    var goalId by rememberSaveable { mutableStateOf(0) }
    var goalTarget by rememberSaveable { mutableStateOf(0) }
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    lateinit var goal : Goal

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text("Goal", style = TextStyle(
                fontFamily = Raleway,
                fontSize = 18.sp
            )
            ) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            selectCategory.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label.goalName
                    goalId = label.goalId
                    goalTarget = label.targetSteps
                    expanded = false
                }) {
                    Text(text = label.goalName)
                    goal.goalId = goalId
                    goal.goalName = selectedText
                    goal.targetSteps = goalTarget
                    goal.status = "ACTIVE"
                    goal.editable = "Y"
                }
            }
        }
    }
    return goal
}


