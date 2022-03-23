package com.mvvm.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.todo.database.MyTaskDatabase
import com.mvvm.todo.model.TaskEntry
import com.mvvm.todo.repo.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = MyTaskDatabase.getDatabase(application).taskDao()
    private val repo: TaskRepo = TaskRepo(taskDao)

    val getAllTask: LiveData<List<TaskEntry>> = repo.getAllTask()

    fun getAllTaksByCat(id: Int): LiveData<List<TaskEntry>> {
        return repo.getAllTaksByCat(id)
    }

    fun getTaskToday(): LiveData<List<TaskEntry>> {
        return repo.getTaskToday()
    }

    suspend fun update(taskEntry: TaskEntry) {
        repo.update(taskEntry)
    }

    fun delete(taskEntry: TaskEntry) {
        repo.delete(taskEntry)
    }

    fun getCat(id: Int): LiveData<List<TaskEntry>> = repo.getTotalUseCategory(id)

    fun insertData(taskEntry: TaskEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertData(taskEntry)
        }
    }

    fun onTaskSelected(taskEntry: TaskEntry) {

    }

    fun onTaskCheckedChanged(taskEntry: TaskEntry, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(taskEntry.copy(complete = isChecked))
    }
}