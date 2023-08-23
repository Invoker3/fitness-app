package com.example.fitnessapp.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGoal(goal: Goal)

    @Query("SELECT * FROM goal")
    fun findAllGoals(): List<Goal>

    @Query("SELECT * FROM goal")
    fun findAllLiveGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM goal WHERE status = 'ACTIVE'")
    fun findActiveGoal(): Goal

    @Query("SELECT goalName FROM goal WHERE goalId = :id")
    fun findGoalName(id: Int): String

    @Query("SELECT targetSteps FROM goal WHERE goalId = :id")
    fun findGoalSteps(id: Int): Int

    @Query("SELECT count(*) FROM goal")
    fun getCount(): Int

    @Query("SELECT count(*) FROM goal where goalName = :name ")
    fun getCountSameGoal(name : String): Int

    @Query("SELECT count(*) FROM goal where targetSteps = :targetSteps")
    fun getSameTarget(targetSteps :Int): Int

    @Query("SELECT count(*) FROM goal where goalName = :name and targetSteps = :targetSteps")
    fun getSameGoal(name : String,targetSteps :Int): Int

    @Query("DELETE FROM goal WHERE goalId = :id")
    fun deleteGoal(id: Int)

    @Query("SELECT goal.targetSteps FROM goal WHERE goalId = :goalId")
    fun getActiveGoalTargetSteps(goalId: Int): Int

    @Query("SELECT targetSteps FROM goal, activity WHERE goal.goalId = activity.goalIdActivity " +
            "AND goal.status = 'ACTIVE' AND activity.date = :date")
    fun getActiveGoalSteps(date: String): Int

    @Query("SELECT id FROM goal JOIN activity ON goal.goalId = activity.goalIdActivity " +
            "AND goal.status = 'ACTIVE' AND activity.date = :date")
    fun getActiveGoalId(date: String): Int

    @Query("UPDATE goal SET targetSteps=:target WHERE goalId = :id")
    fun updateGoalTarget(id: Int, target: Int)

    @Query("UPDATE goal SET goalName=:goalName WHERE goalId = :id")
    fun updateGoalName(id: Int, goalName: String)

    @Query("UPDATE goal SET status=:status WHERE goalId = :id")
    fun updateGoalStatus(id: Int, status: String)

    @Query("UPDATE goal SET status='IN_ACTIVE' WHERE status='ACTIVE'")
    fun updateActiveGoal()

    @Query("UPDATE goal SET editable='N'")
    fun updateGoalEditableNo()

    @Query("UPDATE goal SET editable='Y'")
    fun updateGoalEditableYes()

    @Query("SELECT editable FROM goal WHERE goalId= 1")
    fun getEditableValue(): String

    @Query("SELECT COUNT(*) FROM goal")
    fun count(): Int

    @Insert
    fun insert(goal: Goal?): Long

    @Insert
    fun insertAll(goals: Array<Goal?>?): LongArray?

    @Query("SELECT * FROM goal")
    fun selectAll(): Cursor?

    @Query("SELECT * FROM goal WHERE goal.goalId = :id")
    fun selectById(id: Long): Cursor?

    @Query("DELETE FROM goal WHERE goal.goalId = :id")
    fun deleteById(id: Long): Int

    @Update
    fun update(goal: Goal?): Int
}