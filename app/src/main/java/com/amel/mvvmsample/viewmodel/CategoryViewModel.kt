package com.amel.mvvmsample.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.amel.mvvmsample.model.CategoryData
import com.amel.mvvmsample.model.CategoryTask
import com.amel.mvvmsample.repo.CategoryRepo
import com.amel.mvvmsample.repo.TaskRepo
import com.amel.mvvmsample.ui.fragment.add.Cons.INSERT
import com.amel.mvvmsample.util.Constant.myGlobalVarIdCat
import com.amel.mvvmsample.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepo: CategoryRepo,
    private val taksRepo: TaskRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cat = savedStateHandle.get<CategoryTask>("cat")
    var catName = savedStateHandle.get<String>("catName") ?: cat?.name ?: ""
        set(value) {
            field = value
            savedStateHandle.set("catName", value)
        }

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private val categoryData = MutableLiveData<MutableList<CategoryData>>()
    val category: LiveData<MutableList<CategoryData>>
        get() = categoryData

    init {
        categoryData.value = mutableListOf()
    }

    private fun insertCategory(categoryTask: CategoryTask) = viewModelScope.launch {
        val newRowId = categoryRepo.insert(categoryTask)
        if (newRowId > -1) {
            statusMessage.value = Event("Category Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun updateCategory(categoryTask: CategoryTask) = viewModelScope.launch {
        val noOfRows = categoryRepo.update(categoryTask)
        if (noOfRows > 0) {
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getAllCategories(): LiveData<List<CategoryTask>> {
        return categoryRepo.categories
    }

    fun callDelete(category: CategoryData) {
        deleteCategory(category)
    }
    private fun deleteCategory(category: CategoryData) = viewModelScope.launch {
        val noOfRowsDeleted = categoryRepo.delete(CategoryTask(category.category_id, category.category_name))
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
            viewModelScope.launch {
                taksRepo.deleteByCatId(category.category_id)
            }
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun loadCategory(categoryTask: List<CategoryTask>) {
        categoryData.value?.clear()
        var i = 1
        categoryTask.forEach {
            viewModelScope.launch {
                val use = taksRepo.getTotalUseCategory(it.id)

                Log.d("aaaaaaaaaaaaaa", use.toString())

                categoryData.value?.add(CategoryData(it.id, it.name, use))
                Log.d("aaaaaaaaaaaaaa", categoryData.toString())
                if (categoryTask.size == i) {
                    categoryData.postValue(categoryData.value)
                    i = 1
                }
                i++
            }
        }
    }

    fun onSaveClick(): Boolean {
        if (catName.isBlank()) {
            statusMessage.value = Event("Fields cannot be empty")
            return false
        }
        if (!INSERT) {
            val updateData = CategoryTask(id = myGlobalVarIdCat, name = catName)
            updateCategory(updateData)
        } else {
            val newData = CategoryTask(id = 0, name = catName)
            insertCategory(newData)
        }
        return true
    }
}