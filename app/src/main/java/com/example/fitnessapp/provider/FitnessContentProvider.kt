package com.example.fitnessapp.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import androidx.annotation.Nullable
import androidx.room.Room
import com.example.fitnessapp.database.*

class FitnessContentProvider : ContentProvider() {

    lateinit var db: RoomDB

    companion object {
        private const val AUTHORITY = "com.demo.user.provider"

        val ACTIVITY_URL = Uri.parse(
            "content://$AUTHORITY/activity"
        )
        val GOAL_URL = Uri.parse(
            "content://$AUTHORITY/goal"
        )

        private const val CODE_ACTIVITY_DIR = 1
        private const val CODE_ACTIVITY_ITEM = 2
        private const val CODE_GOAL_DIR = 3
        private const val CODE_GOAL_ITEM = 4

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(
                AUTHORITY,
                "activity",
                CODE_ACTIVITY_DIR
            )
            MATCHER.addURI(
                AUTHORITY,
                "activity/*",
                CODE_ACTIVITY_ITEM
            )
            MATCHER.addURI(
                AUTHORITY,
                "goal",
                CODE_GOAL_DIR
            )
            MATCHER.addURI(
                AUTHORITY,
                "goal/*",
                CODE_GOAL_ITEM
            )
        }
    }

    override fun onCreate(): Boolean {
        db = Room.databaseBuilder(context!!, RoomDB::class.java, "fitnessDB").build()
        return true
    }

    @Nullable
    override fun query(
        uri: Uri, @Nullable projection: Array<String?>?, @Nullable selection: String?,
        @Nullable selectionArgs: Array<String?>?, @Nullable sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        return if (code == CODE_ACTIVITY_DIR || code == CODE_GOAL_ITEM) {
            val context = context ?: return null
            val activity: ActivityDao = RoomDB.getInstance(context).activityDao()
            val cursor: Cursor? = if (code == CODE_ACTIVITY_DIR) {
                activity.selectAll()
            } else {
                activity.selectById(ContentUris.parseId(uri))
            }
            cursor?.setNotificationUri(context.contentResolver, uri)
            cursor
        } else if (code == CODE_GOAL_DIR || code == CODE_GOAL_ITEM) {
            val context = context ?: return null
            val goal: GoalDao = RoomDB.getInstance(context).goalDao()
            val cursor: Cursor? = if (code == CODE_GOAL_DIR) {
                goal.selectAll()
            } else {
                goal.selectById(ContentUris.parseId(uri))
            }
            cursor?.setNotificationUri(context.contentResolver, uri)
            cursor
        } else {
            throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Nullable
    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_ACTIVITY_DIR -> "vnd.android.cursor.dir/activity"
            CODE_ACTIVITY_ITEM -> "vnd.android.cursor.item/activity"
            CODE_GOAL_DIR -> "vnd.android.cursor.dir/goal"
            CODE_GOAL_ITEM -> "vnd.android.cursor.item/goal"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Nullable
    override fun insert(uri: Uri, @Nullable values: ContentValues?): Uri? {
        return when (MATCHER.match(uri)) {
            CODE_ACTIVITY_DIR -> {
                val context = context ?: return null
                val id: Long = RoomDB.getInstance(context).activityDao()
                    .insert(Activity().fromContentValues(values))
                context.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }

            CODE_GOAL_DIR -> {
                val context = context ?: return null
                val id: Long = RoomDB.getInstance(context).goalDao()
                    .insert(Goal().fromContentValues(values))
                context.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }

            CODE_ACTIVITY_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            CODE_GOAL_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")

        }
    }

    override fun delete(
        uri: Uri, @Nullable selection: String?,
        @Nullable selectionArgs: Array<String?>?
    ): Int {
        return when (MATCHER.match(uri)) {
            CODE_ACTIVITY_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_GOAL_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_ACTIVITY_ITEM -> {
                val context = context ?: return 0
                val count: Int = RoomDB.getInstance(context).activityDao()
                    .deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                count
            }

            CODE_GOAL_ITEM -> {
                val context = context ?: return 0
                val count: Int = RoomDB.getInstance(context).goalDao()
                    .deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                count
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
        uri: Uri, @Nullable values: ContentValues?, @Nullable selection: String?,
        @Nullable selectionArgs: Array<String?>?
    ): Int {
        return when (MATCHER.match(uri)) {
            CODE_ACTIVITY_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_GOAL_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")

            CODE_ACTIVITY_ITEM -> {
                val context = context ?: return 0
                val activity: Activity = Activity().fromContentValues(values)
                activity.id = ContentUris.parseId(uri).toInt()
                val count: Int = RoomDB.getInstance(context).activityDao()
                    .update(activity)
                context.contentResolver.notifyChange(uri, null)
                count
            }

            CODE_GOAL_ITEM -> {
                val context = context ?: return 0
                val goal: Goal = Goal().fromContentValues(values)
                goal.goalId = ContentUris.parseId(uri).toInt()
                val count: Int = RoomDB.getInstance(context).goalDao()
                    .update(goal)
                context.contentResolver.notifyChange(uri, null)
                count
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun bulkInsert(uri: Uri, valuesArray: Array<ContentValues?>): Int {
        return when (MATCHER.match(uri)) {
            CODE_ACTIVITY_DIR -> {
                val context = context ?: return 0
                val database: RoomDB = RoomDB.getInstance(context)
                val activities: Array<Activity?> = arrayOfNulls<Activity>(valuesArray.size)
                var i = 0
                while (i < valuesArray.size) {
                    activities[i] = Activity().fromContentValues(valuesArray[i])
                    i++
                }
                database.activityDao().insertAll(activities)!!.size
            }

            CODE_GOAL_DIR -> {
                val context = context ?: return 0
                val database: RoomDB = RoomDB.getInstance(context)
                val goals: Array<Goal?> = arrayOfNulls<Goal>(valuesArray.size)
                var i = 0
                while (i < valuesArray.size) {
                    goals[i] = Goal().fromContentValues(valuesArray[i])
                    i++
                }
                database.goalDao().insertAll(goals)!!.size
            }

            CODE_ACTIVITY_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            CODE_GOAL_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}
