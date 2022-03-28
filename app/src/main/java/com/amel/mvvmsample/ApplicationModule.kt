package com.amel.mvvmsample

import android.content.Context
import androidx.room.Room
import com.amel.mvvmsample.database.MyTaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun provideMyTaskDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MyTaskDatabase::class.java,
        "MyTask"
    ).build()

    @Singleton
    @Provides
    fun provideTaksDao(
        db: MyTaskDatabase
    ) = db.taskDao()

    @Singleton
    @Provides
    fun provideCategoryDao(
        db: MyTaskDatabase
    ) = db.categoryDao()
}