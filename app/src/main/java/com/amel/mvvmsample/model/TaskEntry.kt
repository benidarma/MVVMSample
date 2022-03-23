package com.mvvm.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TaskEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var category: Int,
    var time: String,
    var complete: Boolean = false
) : Parcelable
