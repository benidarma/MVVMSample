package com.mvvm.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mvvm.todo.model.TaskEntry

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntry: TaskEntry)

    @Update
    suspend fun update(taskEntry: TaskEntry)

    @Delete
    fun delete(taskEntry: TaskEntry)

    @Query("SELECT * FROM TaskEntry ORDER BY time DESC")
    fun getAllTask(): LiveData<List<TaskEntry>>

    @Query("SELECT * FROM TaskEntry WHERE category = :id_cat ORDER BY id DESC")
    fun getAllTaskByCat(id_cat: Int): LiveData<List<TaskEntry>>

    @Query("SELECT * from TaskEntry where DATE(time) <= date('now', '+1 day') AND DATE(time) >= date('now', '-1 day')")
    fun getTaskToday(): LiveData<List<TaskEntry>>

    @Query("SELECT * FROM TaskEntry WHERE category = :id ORDER BY id DESC")
    fun getCount(id: Int): LiveData<List<TaskEntry>>
}