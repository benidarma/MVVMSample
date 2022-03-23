package com.mvvm.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.todo.database.MyTaskDatabase
import com.mvvm.todo.model.CategoryTask
import com.mvvm.todo.repo.CategoryRepo
import com.mvvm.todo.util.Constant.myGlobalVarIdCat
import com.mvvm.todo.util.Constant.myGlobalVarTitleCat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryDao = MyTaskDatabase.getDatabase(application).categoryDao()
    private val repo: CategoryRepo = CategoryRepo(categoryDao)

    val getAllCategory: LiveData<List<CategoryTask>> = repo.getAllCategory()

    fun insertData(categoryTask: CategoryTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertData(categoryTask)
        }
    }

    fun onCategoryVSelected(categoryTask: CategoryTask){
        myGlobalVarIdCat = categoryTask.id
        myGlobalVarTitleCat = categoryTask.name
    }

    fun onLongCategoryVSelected(categoryTask: CategoryTask){
        myGlobalVarIdCat = categoryTask.id
        myGlobalVarTitleCat = categoryTask.name
    }
}