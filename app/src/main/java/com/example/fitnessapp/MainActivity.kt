package com.example.fitnessapp

//noinspection SuspiciousImport
import android.R
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.database.Activity
import com.example.fitnessapp.database.RoomDB
import com.example.fitnessapp.provider.FitnessContentProvider
import com.example.fitnessapp.ui.theme.FitnessAppTheme


@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    lateinit var db: RoomDB
    private val activityAdapter: ActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                db = RoomDB.getInstance(this)
                Navigation(db);
            }
        }
    }

    private val mLoaderCallbacks: LoaderManager.LoaderCallbacks<Cursor?> = object :
        LoaderManager.LoaderCallbacks<Cursor?> {
        override fun onCreateLoader(id: Int, @Nullable args: Bundle?): Loader<Cursor?> {
            return CursorLoader(
                applicationContext,
                FitnessContentProvider.ACTIVITY_URL, arrayOf<String>(Activity().id.toString(), Activity().goalIdActivity.toString(),
                    Activity().stepsAchieved.toString(), Activity().date, Activity().historyRecording),
                null, null, null
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
            activityAdapter?.setCheeses(data)
        }

        override fun onLoaderReset(loader: Loader<Cursor?>) {
            activityAdapter?.setCheeses(null)
        }
    }

    private class ActivityAdapter : RecyclerView.Adapter<ActivityAdapter.ViewHolder?>() {
        private var mCursor: Cursor? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return if (mCursor == null)
                0
            else
                mCursor!!.count
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (mCursor!!.moveToPosition(position)) {
                holder.mText.text = mCursor!!.getString(
                    mCursor!!.getColumnIndexOrThrow(Activity().id.toString())
                )
            }
        }

        fun setCheeses(cursor: Cursor?) {
            mCursor = cursor
            notifyDataSetChanged()
        }

        internal class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.simple_list_item_1, parent, false
            )
        ) {
            val mText: TextView

            init {
                mText = itemView.findViewById(R.id.text1)
            }
        }
    }
}
