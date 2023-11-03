package com.example.evaluationexerciselelab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.evaluationexerciselelab.data.local.dao.StudentDao
import com.example.evaluationexerciselelab.data.local.entity.Student

@Database(entities = [Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

}