package com.example.fitnessapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.model.HistoryPageViewModel
import com.example.fitnessapp.ui.theme.Raleway
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserPreferences(navController: NavController, db: RoomDB, goalPageViewModel: GoalPageViewModel,
                    historyPageViewModel: HistoryPageViewModel
) {

    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("User Preferences", style = TextStyle(
                    fontFamily = Raleway,
                    fontSize = 20.sp
                ))
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Screen.HomePage.route)
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            backgroundColor = MaterialTheme.colors.primarySurface
            )
        Column() {
            displayList(navController, db, goalPageViewModel, historyPageViewModel)
        }
    }
}

@Composable
fun displayList(navController: NavController, db: RoomDB, goalPageViewModel: GoalPageViewModel,
                historyPageViewModel: HistoryPageViewModel) {
    val languages = listOf(
        "Goal Editable", "History Recording", "Delete All History"
    )
    var deleteDialog = remember { mutableStateOf(false) }
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDateAndTime = sdf.format(Date())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(languages) { language ->
                if(language == "Delete All History") {
                    Row() {
                        Text(language, modifier = Modifier.padding(15.dp))
                        Button(
                            onClick = { deleteDialog.value = true },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Delete", style = TextStyle(
                                fontFamily = Raleway,
                                fontSize = 15.sp
                            ))
                        }
                        if (deleteDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    deleteDialog.value = false
                                },
                                title = { Text(text = "Delete Activity History", color = Color.Black) },
                                text = { Text(text = "Are you Sure ?", color = Color.Black) },

                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            historyPageViewModel.deleteActivityHistory(
                                                db,
                                                currentDateAndTime
                                            )
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
                    Divider()
                } else if (language == "Goal Editable") {
                    Row() {
                        Text(language, modifier = Modifier.padding(15.dp))
                        val checkedState = remember { mutableStateOf(
                            goalPageViewModel.getEditableValue(db) == "Y"
                        ) }
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                if(checkedState.value) {
                                    goalPageViewModel.updateGoalEditableYes(db)
                                } else {
                                    goalPageViewModel.updateGoalEditableNo(db)
                                }
                            }
                        )
                    }
                    Divider()
                } else {
                    Row() {
                        Text(language, modifier = Modifier.padding(15.dp))
                        val checkedState = remember { mutableStateOf(
                            historyPageViewModel.getHistoryRecordingValue(db) == "Y"
                        ) }
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                if(checkedState.value) {
                                    historyPageViewModel.changeHistoryRecordingYes(db)
                                } else {
                                    historyPageViewModel.changeHistoryRecordingNo(db)
                                }
                            }
                        )
                    }
                    Divider()
                }
            }
        }
        BottomBar(navController)
    }
}