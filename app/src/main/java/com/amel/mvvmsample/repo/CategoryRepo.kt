package com.mvvm.todo.repo

import androidx.lifecycle.LiveData
import com.mvvm.todo.database.CategoryDao
import com.mvvm.todo.model.CategoryTask

class CategoryRepo(val categoryDao: CategoryDao) {
    suspend fun insertData(categoryTask: CategoryTask) = categoryDao.insert(categoryTask)

    fun getAllCategory(): LiveData<List<CategoryTask>> = categoryDao.getAllCategory()
}