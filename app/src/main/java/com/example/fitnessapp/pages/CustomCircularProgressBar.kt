package com.example.fitnessapp.pages

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomCircularProgressBar(
    db : RoomDB,
    homePageViewModel: HomePageViewModel,
    goalPageViewModel: GoalPageViewModel,
    size: Dp = 150.dp,
    indicatorThickness: Dp = 28.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = Color.Blue,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
    dataTextStyle: TextStyle = TextStyle(
        fontFamily = Raleway,
        fontSize = 28.sp
    )
    ){
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDateAndTime = sdf.format(Date())
    var number: Float = if (homePageViewModel.getTotalActivitySteps(db, currentDateAndTime) != 0 ||
        goalPageViewModel.getActiveGoalSteps(db, currentDateAndTime) != 0 ) {
        (homePageViewModel.getTotalActivitySteps(db, currentDateAndTime).toFloat() /
                goalPageViewModel.getActiveGoalSteps(db, currentDateAndTime))
    } else {
        0f
    }
    var numberR by remember {
        mutableStateOf(0f)
    }

    val animateNumber = animateFloatAsState(
        targetValue = numberR,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(Unit) {
        numberR = number
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)
    ) {
        Canvas(
            modifier = Modifier
                .size(size = size)
        ) {
            drawCircle(
                color = backgroundIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round)
            )

            val sweepAngle = (animateNumber.value) * 360
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
            )
        }
        DisplayText(
            dataTextStyle = dataTextStyle,
            db = db,
            homePageViewModel
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
private fun DisplayText(
    dataTextStyle: TextStyle,
    db : RoomDB,
    homePageViewModel: HomePageViewModel
) {
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDateAndTime = sdf.format(Date())
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = homePageViewModel.getTotalActivitySteps(db, currentDateAndTime).toString(),
            style = dataTextStyle,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Steps",
            style = dataTextStyle
        )
        Spacer(modifier = Modifier.height(2.dp))
    }
}
