package com.example.fitnessapp.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.model.HistoryPageViewModel
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun History(navController: NavController, db: RoomDB, historyPageViewModel: HistoryPageViewModel,
            goalPageViewModel: GoalPageViewModel, homePageViewModel: HomePageViewModel) {
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDateAndTime = sdf.format(Date())
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Activity History", style = TextStyle(
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
                if(historyPageViewModel.getAllHistory(db, currentDateAndTime).isEmpty() ||
                    historyPageViewModel.getAllHistory(db, currentDateAndTime).first().historyRecording == "Y") {
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.AddHistory.route)
                            }) {
                            Icon(Icons.Filled.Add, null)
                        }
                }
                IconButton(onClick = {
                    navController.navigate(Screen.UserPreferences.route)
                }) {
                    Icon(Icons.Filled.Settings, null)
                }
            }
        )

        var historyList = db.activityDao().fetchActivityHistory(currentDateAndTime)
        Column() {
            for (i in historyList.indices) {
                Box(Modifier.verticalScroll((rememberScrollState()))) {
                    Column() {
                        ExpandableCard(
                            title = historyList[i].date,
                            activityid=historyPageViewModel.findActivityByDate(db, historyList[i].date),
                            goalName = "Goal Name : " + goalPageViewModel.findGoalName(db, historyList[i].goalIdActivity),
                            goalTarget = "Goal Target : " + goalPageViewModel.findGoalSteps(db,historyList[i].goalIdActivity),
                            stepsAchieved = "Steps Achieved : " + historyList[i].stepsAchieved,
                            navController = navController,
                            db = db, homePageViewModel = homePageViewModel,
                            stepsAchievedInHistory = historyList[i].stepsAchieved,
                            goalTargetInHistory = goalPageViewModel.findGoalSteps(db,historyList[i].goalIdActivity)
                        )
                    }
                }
            }
        }
        BottomBar(navController)
        }
    }