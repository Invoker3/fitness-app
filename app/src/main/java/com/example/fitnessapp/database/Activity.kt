package com.example.fitnessapp.database

import android.content.ContentValues
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Activity",
    foreignKeys = [ForeignKey(
        entity = Goal::class,
        childColumns = ["goalIdActivity"],
        parentColumns = ["goalId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Activity() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "goalIdActivity", index = true)
    var goalIdActivity: Int = 0

    @ColumnInfo(name = "stepsAchieved")
    var stepsAchieved: Int = 0

    @ColumnInfo(name = "date")
    var date: String = ""

    @ColumnInfo(name = "historyRecording")
    var historyRecording: String = ""

//    constructor(id: Int, goalIdActivity: Int, stepsAchieved: Int, date: String, historyRecording: String) :
//            this(0, goalIdActivity, stepsAchieved, date, historyRecording)

    init {
        id
        goalIdActivity
        stepsAchieved
        date
        historyRecording
    }

    fun fromContentValues(@Nullable values: ContentValues?): Activity {
        val activity = Activity()
        if (values != null && values.containsKey("id")) {
            activity.id = values.getAsInteger("id")
        }
        if (values != null && values.containsKey("goalIdActivity")) {
            activity.goalIdActivity = values.getAsInteger("goalIdActivity")
        }
        if (values != null && values.containsKey("stepsAchieved")) {
            activity.stepsAchieved = values.getAsInteger("stepsAchieved")
        }
        if (values != null && values.containsKey("date")) {
            activity.date = values.getAsString("date")
        }
        if (values != null && values.containsKey("historyRecording")) {
            activity.historyRecording = values.getAsString("historyRecording")
        }
        return activity
    }
}