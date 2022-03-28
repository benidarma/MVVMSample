package com.amel.mvvmsample.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amel.mvvmsample.model.TaskEntry

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(taskEntry: TaskEntry) : Long

    @Update
    suspend fun updateTask(taskEntry: TaskEntry) : Int

    @Delete
    suspend fun deleteTask(taskEntry: TaskEntry) : Int

    @Query("DELETE FROM taks")
    suspend fun deleteAllTask() : Int

    @Query("SELECT * FROM taks ORDER BY taks_time ASC")
    fun getAllTask(): LiveData<List<TaskEntry>>

    @Query("SELECT * FROM taks WHERE category_id = :id_cat ORDER BY taks_id DESC")
    fun getAllTaskByCat(id_cat: Int): LiveData<List<TaskEntry>>

    @Query("SELECT * from taks where DATE(taks_time) <= date('now', '+1 day') AND DATE(taks_time) >= date('now', '-1 day')")
    fun getTaskToday(): LiveData<List<TaskEntry>>

    @Query("SELECT COUNT(taks_id) FROM taks WHERE category_id = :id ORDER BY taks_id DESC")
    fun getCount(id: Int): Int

    @Query("DELETE FROM taks WHERE category_id = :id")
    fun deleteByCatId(id: Int)
}