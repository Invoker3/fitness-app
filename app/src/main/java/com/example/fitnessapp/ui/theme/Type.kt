package com.example.fitnessapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.R

val Raleway = FontFamily(
        Font(R.font.raleway_medium)
)

val RalewayBold = FontFamily(
        Font(R.font.raleway_bold)
)

val Typography = Typography(
        body1 = TextStyle(
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        body2 = TextStyle(
                fontFamily = RalewayBold,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        )

)