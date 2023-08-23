package com.example.fitnessapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.Goal
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.ui.theme.Raleway

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoal(navController : NavController, db:RoomDB,
goalPageViewModel: GoalPageViewModel) {
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Add Goal", style = TextStyle(
                    fontFamily = Raleway,
                    fontSize = 20.sp
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Screen.Goals.route)
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
                val focusManager = LocalFocusManager.current
                val focusRequester = remember { FocusRequester() }
                var goal by remember { mutableStateOf(TextFieldValue("")) }
                var checkGoal by remember { mutableStateOf(false) }
                OutlinedTextField(
                    modifier = Modifier.padding(20.dp).focusRequester(focusRequester)
                        .onFocusChanged {
                            checkGoal = goalPageViewModel.getCountSameGoal(db, goal.text) >= 1

                        },
                    value = goal,
                    singleLine = true,
                    label = { Text(text = "Goal Name") },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (goalPageViewModel.getCountSameGoal(db, goal.text) >= 1 ) {
                                checkGoal = true
                            }
                            else {
                                checkGoal = false
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                        }
                    ),
                    onValueChange = {
                        goal = it
                    },
                    supportingText = {
                        if(checkGoal) {
                            Text("Name Already Exist")
                        }
                    },
                    isError = checkGoal
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                var steps by remember { mutableStateOf(TextFieldValue("")) }
                var checkSteps by remember { mutableStateOf(false) }
                var insertGoal = Goal()
                var addGoal = Goal()
                OutlinedTextField(
                    value = steps,
                    singleLine = true,
                    label = { Text(text = "Steps To Achieve Goal") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            var editable = "Y"
                            if (goalPageViewModel.getCount(db) != 0 && goalPageViewModel.getEditableValue(db) == "N")
                                editable = "N"

                            if(goalPageViewModel.getSameTarget(db,steps.text.toInt()) >= 1 )
                            {
                                checkSteps = true
                            }
                            else
                            {
                                insertGoal.goalName = goal.text
                                insertGoal.targetSteps = steps.text.toInt()
                                insertGoal.status = "IN_ACTIVE"
                                insertGoal.status = editable
                                goalPageViewModel.insertGoal(db, insertGoal)
                            }

                        }
                    ),
                    onValueChange = {
                        steps = it
                    },
                    supportingText = {
                        if(checkSteps) {
                            Text("Steps Already Exist")
                        }
                    },
                    isError = checkSteps

                )
                Button(onClick = {
                    if(goalPageViewModel.getCountSameGoal(db, goal.text) >= 1  ) {
                        checkGoal = true
                    } else if (goalPageViewModel.getSameTarget(db, steps.text.toInt()) >= 1){
                        checkSteps = true
                    } else {
                        addGoal.goalName = goal.text
                        addGoal.targetSteps = steps.text.toInt()
                        addGoal.status = "IN_ACTIVE"
                        addGoal.status = "Y"
                        goalPageViewModel.insertGoal(db, addGoal)
                        focusManager.clearFocus()
                        navController.navigate(Screen.Goals.route)
                    }

                },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Add Goal")
                }
            }

        }

        BottomBar(navController)
    }
}

