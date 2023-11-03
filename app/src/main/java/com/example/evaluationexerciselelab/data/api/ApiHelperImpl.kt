package com.example.evaluationexerciselelab.data.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getStudents() = apiService.getStudents()


}