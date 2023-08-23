package com.example.fitnessapp.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.Screen
import com.example.fitnessapp.database.Activity
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun HomePage(navController : NavController, db : RoomDB, homePageViewModel : HomePageViewModel,
             goalPageViewModel: GoalPageViewModel) {
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDateAndTime = sdf.format(Date())
    Column() {

        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Activity Tracker", style = TextStyle(
                    fontFamily = Raleway,
                    fontSize = 20.sp
                    )
                )
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
                .height(200.dp),
            backgroundColor = Color(0xff91a4fc)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(30.dp))
                CustomCircularProgressBar(db, homePageViewModel, goalPageViewModel);
            }
        }

        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(120.dp),
            backgroundColor = Color(0xFFD1D1D1)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var steps by remember { mutableStateOf(TextFieldValue("")) }
                val pattern = remember { Regex("^\\d+\$") }
                var checkSteps by remember { mutableStateOf(false) }
                var activity = Activity()
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (steps.text == null || steps.text == ""|| !pattern.matches(steps.text))
                            {
                                navController.navigate(Screen.HomePage.route)
                                checkSteps = true
                            }
                            else if (homePageViewModel.getCountOfToday(db, currentDateAndTime) == 0 ||
                                db.activityDao() == null
                            ) {
                                activity.goalIdActivity = goalPageViewModel.findActiveGoal(db).goalId
                                activity.stepsAchieved = Integer.parseInt(steps.text)
                                activity.date = currentDateAndTime
                                activity.historyRecording = "Y"
                                homePageViewModel.insertActivity(db, activity)
                            } else {
                                homePageViewModel.addActivitySteps(db,
                                    Integer.parseInt(steps.text),
                                    goalPageViewModel.findActiveGoal(db).goalId,
                                    currentDateAndTime
                                )
                            }
                            navController.navigate(Screen.HomePage.route)
                        }
                    ),
                    isError = checkSteps,
                    singleLine = true,
                    value = steps,
                    label = { Text(text = "Add Steps", fontFamily = Raleway, fontWeight = FontWeight.Bold) },
                    onValueChange = {
                        steps = it
                    },
                )
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(120.dp),
            backgroundColor = Color(0xFFD1D1D1)
        ) {
            Row() {
                if (goalPageViewModel.getCount(db) != 0) {
                    Column(
                        Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            homePageViewModel.fetchCurrentGoal(db),
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue,
                            fontSize = 35.sp,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Text("Goal name", fontWeight = FontWeight.Bold, fontSize = 19.sp)
                    }
                        Column(
                            Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Log.d("homePageViewModel.fetchTargetSteps(db).toString()",
                                homePageViewModel.fetchTargetSteps(db).toString())
                            Text(
                                homePageViewModel.fetchTargetSteps(db).toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue,
                                fontSize = 35.sp,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                            Text("Target steps", fontWeight = FontWeight.Bold, fontSize = 19.sp)
                        }
                    } else {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "No Goal Present, Add Goal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(140.dp),
            backgroundColor = Color(0xFFD1D1D1)
        ) {
            Row() {
                if (goalPageViewModel.getCount(db) != 0) {
                    var result = ((homePageViewModel.getTotalActivitySteps(db, currentDateAndTime)
                        .toDouble() / homePageViewModel.fetchTargetSteps(db)
                        .toDouble()) * 100).roundToInt()
                    result = result.toString().format("%.1f", result).toInt();
                    Log.d("result", result.toString())
                    Column(
                        Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            ("$result %"),
                            fontWeight = FontWeight.Bold, color = Color.Blue, fontSize = 35.sp,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Text("Complete", fontWeight = FontWeight.Bold, fontSize = 19.sp)
                    }


                    Column(
                        Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (homePageViewModel.fetchTargetSteps(db) - homePageViewModel
                                .getTotalActivitySteps(db, currentDateAndTime) <= 0
                        ) {
                            IconButton(onClick = {
                                navController.navigate(Screen.UserPreferences.route)
                            }) {
                                Icon(Icons.Filled.Star, null)
                            }
                            Text("Goal Achieved", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        } else {
                            Text(
                                (homePageViewModel.fetchTargetSteps(db) - homePageViewModel
                                    .getTotalActivitySteps(db, currentDateAndTime)).toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue,
                                fontSize = 35.sp,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                            Text(
                                "Steps To Go",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "No Goal Present, Add Goal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        BottomBar(navController)
    }
}
