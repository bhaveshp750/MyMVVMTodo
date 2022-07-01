package com.example.mymvvmtodo.data

import android.app.Application
import android.provider.Settings
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mymvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject() constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operation
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the Dishes"))
                dao.insert(Task("Do the laundry"))
                dao.insert(Task("Buy Groceries", important = true))
                dao.insert(Task("Prepare Food", completed = true))
                dao.insert(Task("Call mom"))
                dao.insert(Task("Vidhi", important = true))
                dao.insert(Task("BreakFast", completed = true))
                dao.insert(Task("Go to Work"))
                dao.insert(Task("Call Aptaputra"))
            }
        }
    }
}