package com.poison.taskfusion.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poison.taskfusion.database.Database
import com.poison.taskfusion.model.Task
import com.poison.taskfusion.ui.tanggalDipilih
import com.poison.taskfusion.ui.taskName
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {
    var taskData = mutableStateOf(emptyList<Task>())
    var taskDataCalendar = mutableStateOf(emptyList<Task>())

    init {
        viewModelScope.launch {
            fetchingTaskData(taskName = taskName.value).collect{
                taskData.value = it
            }
        }
    }

    init {
        viewModelScope.launch {
            fetchingTaskDataByDate(tanggalDipilih.value).collect {
                taskDataCalendar.value = it
            }
        }
    }

    fun createNewTask(
        inputTaskTitle: String,
        inputTaskDesc: String,
        inputTaskDate: String,
        inputTaskPriority: String,
        inputTaskCategory: String
    ) {
        Database.realm.writeBlocking {
            this.copyToRealm(
                Task().apply {
                    taskTitle = inputTaskTitle
                    taskDescription = inputTaskDesc
                    taskDate = inputTaskDate
                    taskPriority = inputTaskPriority
                    taskCategory = inputTaskCategory
                }
            )
        }
    }

    private fun fetchingTaskData(taskName: String): Flow<List<Task>> {
        return if (taskName.isEmpty()) {
            Database.realm.query<Task>().asFlow().map { it.list }
        } else {
            Database.realm.query<Task>("taskTitle == $0", taskName).asFlow().map { it.list }
        }

    }

    private fun fetchingTaskDataByDate(taskDate: String): Flow<List<Task>> {
        return Database.realm.query<Task>("taskDate == $0", taskDate).asFlow().map { it.list }
    }

    fun deleteTask(task: Task) {
        Database.realm.writeBlocking {
            val taskToDelete: Task = this.query<Task>("id == $0", task.id).find().first()
            delete(taskToDelete)
        }
    }

    fun editTaskStatus(task: Task, statusValue: String) {
        Database.realm.writeBlocking {
            val updateTaskStatus: Task? = this.query<Task>("id == $0", task.id).first().find()
            updateTaskStatus?.taskStatus = statusValue
        }
    }
}