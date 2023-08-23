package com.example.fitnessapp.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.Goal
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Goals(navController : NavController, db:RoomDB, goalViewModel:GoalPageViewModel,
          homePageViewModel: HomePageViewModel) {
    var n_goals: Int = goalViewModel.getCount(db)
    var editValue = false
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Goals", style = TextStyle(
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
                    navController.navigate(Screen.AddGoal.route)
                }) {
                    Icon(Icons.Filled.Add, null)
                }
                IconButton(onClick = {
                    navController.navigate(Screen.UserPreferences.route)
                }) {
                    Icon(Icons.Filled.Settings, null)
                }
            })
        var goals: List<Goal> = goalViewModel.findAllGoals(db)
        for (i in 0 until n_goals) {

            if (goals[i].status == "ACTIVE") {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    border = BorderStroke(width = 5.dp, color = Color.Blue),
                    onClick = {})
                {
                    Text(goals[i].goalName)
                    Spacer(modifier = Modifier.width(width = 50.dp))
                    Text(goals[i].targetSteps.toString())
                    Spacer(modifier = Modifier.width(width = 50.dp))
                    IconButton(enabled = false, onClick = {
                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }


                    IconButton(enabled= false, onClick = {
                        navController.navigate(Screen.EditGoals.route)
                    }) {
                        Icon(Icons.Filled.Edit, null)
                    }
                }
            }
            else {
                val openDialog = remember { mutableStateOf(false) }
                val deleteDialog = remember { mutableStateOf(false) }
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    border = BorderStroke(width = 2.dp, color = Color.Black),
                    onClick = {
                        openDialog.value = true
                    })
                {
                    Text(goals[i].goalName)
                    Spacer(modifier = Modifier.width(width = 50.dp))
                    Text(goals[i].targetSteps.toString())
                    Spacer(modifier = Modifier.width(width = 50.dp))

                            IconButton(onClick = {
                                deleteDialog.value=true
                            }) {
                                Icon(Icons.Filled.Delete, null)
                            }
                            if(goalViewModel.getEditableValue(db) == "Y")
                                editValue = true
                            IconButton(enabled= editValue, onClick = {
                                navController.navigate("edit_goals/" + goals[i].goalId)
                            }) {
                                Icon(Icons.Filled.Edit, null)
                            }
                        }
                if (openDialog.value)
                {
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    val currentDateAndTime = sdf.format(Date())
                    AlertDialog(
                        onDismissRequest = {
                            openDialog.value = false },
                        title = { Text(text = "Change Active Goal", color = Color.Black) },
                        text = { Text(text = "Are you Sure ?", color = Color.Black) },

                        confirmButton = {
                            TextButton(
                                onClick = {
                                    goalViewModel.updateActiveGoal(db)
                                    goalViewModel.updateGoalStatus(db, goals[i].goalId,"ACTIVE")
                                    homePageViewModel.updateActivityOnActiveGoalChange(db, goals[i].goalId, currentDateAndTime)
                                    openDialog.value = false
                                }) {
                                Text(text = "Confirm", color = Color.Black)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    openDialog.value = false
                                }) {
                                Text(text = "Dismiss", color = Color.Black)
                            }
                        },
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )
                }
                else if (deleteDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            deleteDialog.value = false },
                        title = { Text(text = "Delete Goal", color = Color.Black) },
                        text = { Text(text = "Are you Sure ?", color = Color.Black) },

                        confirmButton = {
                            TextButton(
                                onClick = {
                                    goalViewModel.deleteGoal(db, goals[i].goalId)
                                    deleteDialog.value = false
                                }) {
                                Text(text = "Confirm", color = Color.Black)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    deleteDialog.value = false
                                }) {
                                Text(text = "Dismiss", color = Color.Black)
                            }
                        },
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )
                }
                    }
                }
                BottomBar(navController)
            }
        }


