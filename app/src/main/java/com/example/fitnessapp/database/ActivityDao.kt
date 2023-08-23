package com.example.fitnessapp.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActivityDao {

        @Insert //(onConflict = OnConflictStrategy.REPLACE)
        fun insertActivity(activity: Activity)

        @Query("SELECT * FROM activity")
        fun getAllActivity(): LiveData<List<Activity>>

        @Query("SELECT count(*) FROM activity")
        fun getActivityCount(): Int

        @Query("SELECT id FROM activity WHERE date = :date")
        fun findActivityByDate(date : String): Int

        @Query("SELECT * FROM activity WHERE goalIdActivity = :goalId")
        fun findActivity(goalId : Int): List<Activity>

        @Query("SELECT stepsAchieved FROM activity WHERE date = :date")
        fun getTotalActivitySteps(date: String): Int

        @Query("SELECT stepsAchieved FROM activity WHERE id = :id")
        fun getTotalActivityStepsByID(id : Int): Int

        @Query("UPDATE activity SET stepsAchieved = :steps + stepsAchieved WHERE goalIdActivity = :id AND date = :date")
        fun addActivitySteps(steps: Int, id: Int, date: String)

        @Query("SELECT count(*) FROM activity WHERE date = :date")
        fun getCountOfToday(date: String): Int

        @Query("UPDATE activity SET goalIdActivity = :goalId WHERE activity.date = :date")
        fun updateActivityOnActiveGoalChange(goalId: Int, date: String)

        @Query("SELECT * FROM activity WHERE activity.date != :date")
        fun fetchActivityHistory(date: String): List<Activity>

        @Query("DELETE FROM activity WHERE activity.date != :date")
        fun deleteActivityHistory(date: String)

        @Query("UPDATE activity SET historyRecording='Y' ")
        fun changeHistoryRecordingYes()

        @Query("UPDATE activity SET historyRecording='N' ")
        fun changeHistoryRecordingNo()

        @Query("SELECT historyRecording FROM activity WHERE id = 1")
        fun getHistoryRecordingValue(): String

        @Query("SELECT count(*)FROM activity WHERE activity.date = :date")
        fun dateCheck(date: String): Int

        @Query("UPDATE activity SET stepsAchieved = :steps, goalIdActivity = :goalIdActivity WHERE id = :id")
        fun updateActivityHistory(steps: Int, id: Int, goalIdActivity: Int)

        @Query("SELECT COUNT(*) FROM activity")
        fun count(): Int

        @Insert
        fun insert(activity: Activity?): Long

        @Insert
        fun insertAll(activities: Array<Activity?>?): LongArray?

        @Query("SELECT * FROM activity")
        fun selectAll(): Cursor?

        @Query("SELECT * FROM activity WHERE activity.id = :id")
        fun selectById(id: Long): Cursor?

        @Query("DELETE FROM activity WHERE activity.id = :id")
        fun deleteById(id: Long): Int

        @Update
        fun update(activity: Activity?): Int
}
