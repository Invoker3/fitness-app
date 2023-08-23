package com.example.fitnessapp.database

import android.content.ContentValues
import androidx.annotation.Nullable
import androidx.room.*

@Entity(
    tableName = "Goal",
    indices = [Index(
        value = ["goalName", "targetSteps", "targetSteps"],
        unique = true
    )]
)
class Goal() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "goalId")
    var goalId: Int = 0

    @ColumnInfo(name = "targetSteps")
    var targetSteps: Int = 0

    @ColumnInfo(name = "goalName")
    var goalName: String = ""

    @ColumnInfo(name = "status")
    var status: String = ""

    @ColumnInfo(name = "editable")
    var editable: String = ""

//    constructor(goalName: String, targetSteps: Int, status: String, editable: String) :
//    this(0,goalName, targetSteps, status, editable)

    init {
        goalId
        goalName
        targetSteps
        status
        editable
    }

    fun fromContentValues(@Nullable values: ContentValues?): Goal {
        val goal = Goal()
        if (values != null && values.containsKey("goalId")) {
            goal.goalId = values.getAsInteger("goalId")
        }
        if (values != null && values.containsKey("goalName")) {
            goal.goalName = values.getAsString("goalName")
        }
        if (values != null && values.containsKey("targetSteps")) {
            goal.targetSteps = values.getAsInteger("targetSteps")
        }
        if (values != null && values.containsKey("status")) {
            goal.status = values.getAsString("status")
        }
        if (values != null && values.containsKey("editable")) {
            goal.editable = values.getAsString("editable")
        }
        return goal
    }
}

