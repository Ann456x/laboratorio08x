package com.example.lab08.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab08.Task
import com.example.lab08.TaskDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}