package com.example.fitnessapp.model

import androidx.lifecycle.ViewModel
import com.example.fitnessapp.database.Activity
import com.example.fitnessapp.database.Goal
import com.example.fitnessapp.database.RoomDB

class HistoryPageViewModel:ViewModel() {
    fun insertHistory(db: RoomDB, activity: Activity) {
        return db.activityDao().insertActivity(activity)
    }

    fun getAllHistory(db: RoomDB, date: String): List<Activity> {
        return db.activityDao().fetchActivityHistory(date)
    }

    fun deleteActivityHistory(db: RoomDB, date: String) {
        return db.activityDao().deleteActivityHistory(date)
    }

    fun changeHistoryRecordingYes(db: RoomDB) {
        return db.activityDao().changeHistoryRecordingYes()
    }

    fun changeHistoryRecordingNo(db: RoomDB) {
        return db.activityDao().changeHistoryRecordingNo()
    }

    fun getHistoryRecordingValue(db: RoomDB): String {
        return db.activityDao().getHistoryRecordingValue()
    }

    fun dateCheck(db: RoomDB, date: String): Int {
        return db.activityDao().dateCheck(date)
    }
    fun findActivityByDate(db: RoomDB, date: String): Int {
        return db.activityDao().findActivityByDate(date)
    }
}