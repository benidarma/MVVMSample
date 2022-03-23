package com.mvvm.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mvvm.todo.model.CategoryTask

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(categoryTask: CategoryTask)

    @Update
    suspend fun update(categoryTask: CategoryTask)

    @Delete
    suspend fun delete(categoryTask: CategoryTask)

    @Query("SELECT * FROM CategoryTask ORDER BY id DESC")
    fun getAllCategory(): LiveData<List<CategoryTask>>
}