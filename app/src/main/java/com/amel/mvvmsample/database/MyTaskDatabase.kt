package com.mvvm.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvvm.todo.model.CategoryTask
import com.mvvm.todo.model.TaskEntry

@Database(entities = [TaskEntry::class, CategoryTask::class], version = 1, exportSchema = false)
abstract class MyTaskDatabase: RoomDatabase(){
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object{
        @Volatile
        private var INSTANCE: MyTaskDatabase? = null

        fun getDatabase(context: Context): MyTaskDatabase{
            synchronized(lock = this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyTaskDatabase::class.java,
                        "MyTask"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}