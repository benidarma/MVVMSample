package com.amel.mvvmsample.viewmodel

import androidx.lifecycle.*
import com.amel.mvvmsample.model.TaskEntry
import com.amel.mvvmsample.repo.TaskRepo
import com.amel.mvvmsample.ui.fragment.add.Cons
import com.amel.mvvmsample.util.Constant
import com.amel.mvvmsample.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepo: TaskRepo,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val ta = savedStateHandle.get<TaskEntry>("ta")
    var taTitle = savedStateHandle.get<String>("taTitle") ?: ta?.title ?: ""
        set(value) {
            field = value
            savedStateHandle.set("taTitle", value)
        }
    var taTime = savedStateHandle.get<String>("taTime") ?: ta?.time ?: ""
        set(value) {
            field = value
            savedStateHandle.set("taTime", value)
        }

    val taks: LiveData<List<TaskEntry>> = taskRepo.taks

    val taksToday:  LiveData<List<TaskEntry>> = taskRepo.taskToday

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun getAllTaksByCat(id: Int): LiveData<List<TaskEntry>> {
        return taskRepo.getAllTaksByCat(id)
    }

    fun callDelete(taskEntry: TaskEntry) {
        deleteTalk(taskEntry)
    }
    private fun deleteTalk(taskEntry: TaskEntry) = viewModelScope.launch {
        val noOfRowsDeleted = taskRepo.delete(taskEntry)
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun onSaveClick(): Boolean {
        if (taTitle.isBlank()) {
            statusMessage.value = Event("Fields cannot be empty")
            return false
        }
        if (taTime.isBlank()) {
            statusMessage.value = Event("Please set time")
            return false
        }
        if (!Cons.INSERT) {
            val updateData = TaskEntry(
                id = Constant.myGlobalVarIdTaks,
                title = taTitle,
                category = Constant.myGlobalVarIdCat,
                time = taTime,
                complete = false
            )
            updateTalk(updateData)
        } else {
            val newData = TaskEntry(
                id = 0,
                title = taTitle,
                category = Constant.myGlobalVarIdCat,
                time = taTime,
                complete = false
            )
            insertTalk(newData)
        }
        return true
    }

    private fun insertTalk(taskEntry: TaskEntry) = viewModelScope.launch {
        val newRowId = taskRepo.insert(taskEntry)
        if (newRowId > -1) {
            statusMessage.value = Event("Taks Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun updateTalk(taskEntry: TaskEntry) = viewModelScope.launch {
        val noOfRows = taskRepo.update(taskEntry)
        if (noOfRows > 0) {
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun onTaskCheckedChanged(taskEntry: TaskEntry, isChecked: Boolean) = viewModelScope.launch {
        taskRepo.update(taskEntry.copy(complete = isChecked))
    }

    fun deleteByCatId(categoryId: Int) {
        viewModelScope.launch {
            taskRepo.deleteByCatId(categoryId)
        }
    }
}