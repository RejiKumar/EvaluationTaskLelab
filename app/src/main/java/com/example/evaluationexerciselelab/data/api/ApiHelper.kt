package com.example.evaluationexerciselelab.data.api

import com.example.evaluationexerciselelab.data.model.ApiStudentsData

interface ApiHelper {

    suspend fun getStudents(): ApiStudentsData

}