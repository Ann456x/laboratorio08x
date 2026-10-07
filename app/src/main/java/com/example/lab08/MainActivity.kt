package com.example.lab08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lab08.data.AppDatabase
import com.example.lab08.repository.TaskRepository
import com.example.lab08.ui.TaskScreen
import com.example.lab08.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val repository = TaskRepository(database.taskDao())
        val viewModel = TaskViewModel(repository)

        setContent {
            TaskScreen(viewModel = viewModel)
        }
    }
}