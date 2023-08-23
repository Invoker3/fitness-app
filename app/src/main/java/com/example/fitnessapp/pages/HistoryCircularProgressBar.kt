package com.example.fitnessapp.pages

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.ui.theme.Raleway

@Composable
fun HistoryCircularProgressBar(
    db : RoomDB,
    homePageViewModel: HomePageViewModel,
    size: Dp = 100.dp,
    indicatorThickness: Dp = 28.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = Color.Blue,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
    dataTextStyle: TextStyle = TextStyle(
        fontFamily = Raleway,
        fontSize = 28.sp
    ),
    goalTarget: Int,
    stepsAchieved: Int
    ){
    val number: Float = stepsAchieved.toFloat() / goalTarget.toFloat()
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
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(2.dp))
    }
}
