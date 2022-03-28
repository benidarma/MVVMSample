package com.amel.mvvmsample.repo

import androidx.lifecycle.LiveData
import com.amel.mvvmsample.database.CategoryDao
import com.amel.mvvmsample.model.CategoryTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepo @Inject constructor(
    private val categoryDao: CategoryDao
){

    val categories: LiveData<List<CategoryTask>> = categoryDao.getAllCategories()

    suspend fun insert(categoryTask: CategoryTask): Long {
        return categoryDao.insertCategory(categoryTask)
    }

    suspend fun update(categoryTask: CategoryTask): Int {
        return categoryDao.updateCategory(categoryTask)
    }

    suspend fun delete(categoryTask: CategoryTask): Int {
        return categoryDao.deleteCategory(categoryTask)
    }

    suspend fun deleteAll(): Int {
        return categoryDao.deleteAllCategories()
    }
}