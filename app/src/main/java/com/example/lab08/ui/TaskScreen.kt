package com.example.lab08

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val currentFilter by viewModel.filter.collectAsState()

    var newTaskDescription by remember { mutableStateOf("") }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var editedText by remember { mutableStateOf("") }

    val filteredTasks = when (currentFilter) {
        "Pendientes" -> tasks.filter { !it.isCompleted }
        "Completadas" -> tasks.filter { it.isCompleted }
        else -> tasks
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lab08 - MVVM + UDF + Room",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskDescription,
                onValueChange = { newTaskDescription = it },
                label = { Text("Nueva Tarea") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTaskDescription.isNotBlank()) {
                    viewModel.addTask(newTaskDescription)
                    newTaskDescription = ""
                }
            }) {
                Text("Agregar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Todas", "Pendientes", "Completadas").forEach { option ->
                FilterChip(
                    selected = currentFilter == option,
                    onClick = { viewModel.setFilter(option) },
                    label = { Text(option) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredTasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { viewModel.toggleTaskCompletion(task) }
                        )

                        Text(
                            text = task.description,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        IconButton(onClick = {
                            taskToEdit = task
                            editedText = task.description
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }

                        IconButton(onClick = { viewModel.deleteTask(task) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.deleteAllTasks() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar todas las tareas")
        }
    }

    taskToEdit?.let { task ->
        AlertDialog(
            onDismissRequest = { taskToEdit = null },
            title = { Text("Editar Tarea") },
            text = {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    label = { Text("Descripción") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (editedText.isNotBlank()) {
                        viewModel.updateTaskDescription(task, editedText)
                        taskToEdit = null
                    }
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { taskToEdit = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}