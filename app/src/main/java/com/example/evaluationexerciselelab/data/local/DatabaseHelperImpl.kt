package com.example.evaluationexerciselelab.data.local

import com.example.evaluationexerciselelab.data.local.entity.Student


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getStudents(): List<Student> = appDatabase.studentDao().getAll()

    override suspend fun insertAll(students: List<Student>) = appDatabase.studentDao().insertAll(students)

}