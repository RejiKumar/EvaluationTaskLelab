package com.example.evaluationexerciselelab.data.local

import com.example.evaluationexerciselelab.data.local.entity.Student

interface DatabaseHelper {

    suspend fun getStudents(): List<Student>

    suspend fun insertAll(students: List<Student>)

}