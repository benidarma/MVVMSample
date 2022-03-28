package com.amel.mvvmsample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taks")
data class TaskEntry(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taks_id")
    var id: Int,

    @ColumnInfo(name = "taks_title")
    var title: String,

    @ColumnInfo(name = "category_id")
    var category: Int,

    @ColumnInfo(name = "taks_time")
    var time: String,

    @ColumnInfo(name = "taks_complete")
    var complete: Boolean = false
)