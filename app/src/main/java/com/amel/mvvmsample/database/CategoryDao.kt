package com.amel.mvvmsample.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amel.mvvmsample.model.CategoryTask

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(categoryTask: CategoryTask) : Long

    @Update
    suspend fun updateCategory(categoryTask: CategoryTask) : Int

    @Delete
    suspend fun deleteCategory(categoryTask: CategoryTask) : Int

    @Query("DELETE FROM category")
    suspend fun deleteAllCategories() : Int

    @Query("SELECT * FROM category ORDER BY category_id DESC")
    fun getAllCategories(): LiveData<List<CategoryTask>>
}