package com.example.fitnessapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.database.Activity
import com.example.fitnessapp.database.RoomDB

class HomePageViewModel : ViewModel(){

    fun fetchCurrentGoal(db: RoomDB):String {
        return db.goalDao().findActiveGoal().goalName
    }
    fun fetchTargetSteps(db: RoomDB):Int{
        return db.goalDao().findActiveGoal().targetSteps
    }

    fun insertActivity(db: RoomDB, activity: Activity) {
        return db.activityDao().insertActivity(activity)
    }

    fun getAllActivity(db: RoomDB): LiveData<List<Activity>> {
        return db.activityDao().getAllActivity()
    }

    fun getActivityCount(db: RoomDB): Int {
        return db.activityDao().getActivityCount()
    }

    fun getTotalActivityStepsByID(db: RoomDB, id: Int): Int {
        return db.activityDao().getTotalActivityStepsByID(id)
    }

    fun findActivity(db: RoomDB, goalId : Int): List<Activity> {
        return db.activityDao().findActivity(goalId)
    }

    fun getTotalActivitySteps(db: RoomDB, date: String): Int {
        return db.activityDao().getTotalActivitySteps(date)
    }

    fun addActivitySteps(db: RoomDB, steps: Int, id: Int, date: String) {
        return db.activityDao().addActivitySteps(steps, id, date)
    }

    fun getCountOfToday(db: RoomDB, date: String): Int {
        return db.activityDao().getCountOfToday(date)
    }

    fun updateActivityOnActiveGoalChange(db: RoomDB, goalId: Int, date: String) {
        return db.activityDao().updateActivityOnActiveGoalChange(goalId, date)
    }

    fun updateActivityHistory(db: RoomDB, id: Int, steps: Int, goalIdActivity: Int) {
        return db.activityDao().updateActivityHistory(steps, id, goalIdActivity)
    }

}