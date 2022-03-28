package com.amel.mvvmsample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amel.mvvmsample.model.CategoryTask
import com.amel.mvvmsample.model.TaskEntry

@Database(
    entities = [TaskEntry::class, CategoryTask::class],
    version = 1, exportSchema = false
)
abstract class MyTaskDatabase: RoomDatabase(){
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}