package com.example.fitnessapp


sealed class Screen (val route: String)
{
    object HomePage : Screen( "home_screen")
    object AddGoal : Screen( "add_goal")
    object Goals : Screen("goals")
    object AddSteps : Screen("add_steps")
    object History : Screen("history")
    object EditGoals : Screen("edit_goals/{id}")
    object UserPreferences : Screen("preferences")
    object EditHistory : Screen("edit_history/{id}")
    object AddHistory : Screen("add_history")
}
