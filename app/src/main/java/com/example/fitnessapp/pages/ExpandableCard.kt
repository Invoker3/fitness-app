package com.example.fitnessapp.pages

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.model.HomePageViewModel

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    activityid: Int,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    goalName: String,
    goalTarget: String,
    stepsAchieved: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 10.dp,
    navController: NavController,
    db: RoomDB,
    homePageViewModel: HomePageViewModel,
    stepsAchievedInHistory: Int,
    goalTargetInHistory: Int
) {
    var expandedState by remember { mutableStateOf(true) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        shape =RoundedCornerShape(20.dp),
        backgroundColor = Color(0xff91a4fc),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                ),
            ),

        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Row() {
                    Column() {
                        Text(
                            text = goalName,
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = goalTarget,
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stepsAchieved,
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis
                        )
                        Button(onClick = {
                            navController.navigate("edit_history/$activityid")
                        },
                            shape = RoundedCornerShape(20.dp))
                        {
                            Text("Edit")
                        }
                    }
                    Spacer(modifier = Modifier.width(width = 53.dp))
                        HistoryCircularProgressBar(db, homePageViewModel, goalTarget = goalTargetInHistory,
                            stepsAchieved = stepsAchievedInHistory)
                }
            }
        }
    }
}

