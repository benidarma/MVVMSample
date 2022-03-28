package com.amel.mvvmsample.repo

import androidx.lifecycle.LiveData
import com.amel.mvvmsample.database.TaskDao
import com.amel.mvvmsample.model.TaskEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepo @Inject constructor(
    private val taskDao: TaskDao
){

    val taks: LiveData<List<TaskEntry>> = taskDao.getAllTask()

    suspend fun insert(taskEntry: TaskEntry): Long {
        return taskDao.insertTask(taskEntry)
    }

    suspend fun update(taskEntry: TaskEntry): Int {
        return taskDao.updateTask(taskEntry)
    }

    suspend fun delete(taskEntry: TaskEntry): Int {
        return taskDao.deleteTask(taskEntry)
    }

    suspend fun deleteAllTaks(): Int {
        return taskDao.deleteAllTask()
    }

    fun getAllTaksByCat(id: Int): LiveData<List<TaskEntry>> = taskDao.getAllTaskByCat(id)

    val taskToday: LiveData<List<TaskEntry>> = taskDao.getTaskToday()

    fun getTotalUseCategory(id: Int): Int = taskDao.getCount(id)

    fun deleteByCatId(id: Int) = taskDao.deleteByCatId(id)
}