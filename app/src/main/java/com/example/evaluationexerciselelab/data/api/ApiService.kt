package com.example.evaluationexerciselelab.data.api

import com.example.evaluationexerciselelab.data.model.ApiStudentsData
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getStudents(): ApiStudentsData

}