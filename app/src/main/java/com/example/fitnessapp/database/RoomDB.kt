package com.example.fitnessapp.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.*

@Database(entities = [Goal::class, Activity::class], version = 10)
abstract class RoomDB : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun activityDao(): ActivityDao

    companion object {
        private var INSTANCE: RoomDB? = null
        @SuppressLint("SimpleDateFormat")
        fun getInstance(context: Context): RoomDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java,
                        "FitnessApp"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

