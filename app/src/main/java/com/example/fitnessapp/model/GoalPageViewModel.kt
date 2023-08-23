package com.example.fitnessapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.database.Goal
import com.example.fitnessapp.database.RoomDB

class GoalPageViewModel:ViewModel() {
    fun insertGoal(db: RoomDB, goal: Goal) {
        return db.goalDao().insertGoal(goal)
    }

    fun findAllGoals(db: RoomDB): List<Goal> {
        return db.goalDao().findAllGoals()
    }

    fun findAllLiveGoals(db: RoomDB): LiveData<List<Goal>> {
        return db.goalDao().findAllLiveGoals()
    }

    fun findActiveGoal(db: RoomDB): Goal {
        return db.goalDao().findActiveGoal()
    }

    fun findGoalName(db: RoomDB, id: Int): String {
        return db.goalDao().findGoalName(id)
    }

    fun findGoalSteps(db: RoomDB, id: Int): Int {
        return db.goalDao().findGoalSteps(id)
    }

    fun getCount(db: RoomDB): Int {
        return db.goalDao().getCount()
    }

    fun getCountSameGoal(db: RoomDB, name : String): Int {
        return db.goalDao().getCountSameGoal(name)
    }
    fun getSameGoal(db: RoomDB, name : String,targetSteps : Int): Int {
        return db.goalDao().getSameGoal(name, targetSteps  )
    }
    fun getSameTarget(db: RoomDB,targetSteps : Int): Int {
        return db.goalDao().getSameTarget(targetSteps)
    }

    fun deleteGoal(db: RoomDB, id : Int) {
        return db.goalDao().deleteGoal(id)
    }


    fun getActiveGoalTargetSteps(db: RoomDB, goalId: Int): Int {
        return db.goalDao().getActiveGoalTargetSteps(goalId)
    }

    fun getActiveGoalSteps(db: RoomDB, date: String): Int {
        return db.goalDao().getActiveGoalSteps(date)
    }

    fun getActiveGoalId(db: RoomDB, date: String): Int {
        return db.goalDao().getActiveGoalId(date)
    }

    fun updateGoalTarget(db: RoomDB, id: Int, target: Int) {
        return db.goalDao().updateGoalTarget(id, target)
    }

    fun updateGoalName(db: RoomDB, id: Int, goalName: String) {
        return db.goalDao().updateGoalName(id, goalName)
    }

    fun updateGoalStatus(db: RoomDB, id: Int, status: String) {
        return db.goalDao().updateGoalStatus(id, status)
    }

    fun updateActiveGoal(db: RoomDB) {
        return db.goalDao().updateActiveGoal()
    }

    fun updateGoalEditableNo(db: RoomDB) {
        return db.goalDao().updateGoalEditableNo()
    }

    fun updateGoalEditableYes(db: RoomDB) {
        return db.goalDao().updateGoalEditableYes()
    }

    fun getEditableValue(db: RoomDB): String {
        return db.goalDao().getEditableValue()
    }
}