package com.example.lab08.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.Task
import com.example.lab08.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state: StateFlow<TaskState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.tasks.collect { taskList ->
                _state.update { it.copy(tasks = taskList) }
            }
        }
    }

    fun onDescriptionChange(newDescription: String) {
        _state.update { it.copy(newTaskDescription = newDescription) }
    }

    fun addTask() {
        viewModelScope.launch {
            repository.addTask(_state.value.newTaskDescription)
            _state.update { it.copy(newTaskDescription = "") }
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            repository.toggleTaskStatus(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}