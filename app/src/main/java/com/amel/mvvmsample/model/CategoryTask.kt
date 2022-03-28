package com.amel.mvvmsample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryTask(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    var id: Int,

    @ColumnInfo(name = "category_name")
    var name: String

)