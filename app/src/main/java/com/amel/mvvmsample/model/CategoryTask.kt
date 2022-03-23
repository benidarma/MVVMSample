package com.mvvm.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CategoryTask(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String
) : Parcelable