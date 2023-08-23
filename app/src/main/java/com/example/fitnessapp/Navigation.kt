package com.example.fitnessapp

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.model.GoalPageViewModel
import com.example.fitnessapp.model.HistoryPageViewModel
import com.example.fitnessapp.model.HomePageViewModel
import com.example.fitnessapp.pages.*

@ExperimentalMaterialApi
@Composable
fun Navigation(db : RoomDB)
{
    val navController = rememberNavController()
    val homePageViewModel = viewModel<HomePageViewModel>()
    val goalPageViewModel = viewModel<GoalPageViewModel>()
    val historyPageViewModel = viewModel<HistoryPageViewModel>()
    NavHost(navController = navController, startDestination = Screen.HomePage.route){
        composable(route = Screen.HomePage.route){

            HomePage(navController = navController, db, homePageViewModel, goalPageViewModel)
        }
        composable(route = Screen.AddGoal.route){
            AddGoal(navController = navController, db, goalPageViewModel)
        }
        composable(route = Screen.Goals.route){
            Goals(navController = navController, db, goalPageViewModel, homePageViewModel)
        }
        composable(route = Screen.EditGoals.route,
        arguments = listOf(navArgument("id"){
            type = NavType.IntType
        })){
            EditGoals(navController = navController, db, goalPageViewModel,
                it.arguments?.getInt("id").toString())
        }
        composable(route = Screen.AddSteps.route){
            AddSteps(navController = navController)
        }
        composable(route = Screen.History.route){
            History(navController = navController, db, historyPageViewModel, goalPageViewModel,
            homePageViewModel)
        }
        composable(route = Screen.UserPreferences.route){
            UserPreferences(navController = navController, db, goalPageViewModel,
                historyPageViewModel)
        }
        composable(route = Screen.EditHistory.route,
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            }
        )){
            EditHistory(navController = navController,db,
                it.arguments?.getInt("id").toString(), homePageViewModel)
        }
        composable(route = Screen.AddHistory.route){
            AddHistory(navController = navController, db, homePageViewModel, historyPageViewModel)
        }
    }
}