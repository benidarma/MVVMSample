package com.mvvm.todo.repo

import androidx.lifecycle.LiveData
import com.mvvm.todo.database.TaskDao
import com.mvvm.todo.model.TaskEntry

class TaskRepo(val taskDao: TaskDao) {
    suspend fun insertData(taskEntry: TaskEntry) = taskDao.insert(taskEntry)

    fun delete(taskEntry: TaskEntry) = taskDao.delete(taskEntry)

    suspend fun update(taskEntry: TaskEntry) = taskDao.update(taskEntry)

    fun getAllTask(): LiveData<List<TaskEntry>> = taskDao.getAllTask()

    fun getAllTaksByCat(id: Int): LiveData<List<TaskEntry>> = taskDao.getAllTaskByCat(id)

    fun getTaskToday(): LiveData<List<TaskEntry>> = taskDao.getTaskToday()

    fun getTotalUseCategory(id: Int): LiveData<List<TaskEntry>> = taskDao.getCount(id)
}