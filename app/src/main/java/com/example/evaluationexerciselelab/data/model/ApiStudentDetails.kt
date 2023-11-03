package com.example.evaluationexerciselelab.data.model

import com.google.gson.annotations.SerializedName

data class ApiStudentsData(

    @field:SerializedName("students")
    val students: List<ApiStudentsItem>,

    @field:SerializedName("status")
    val status: String? = null
)

data class ApiStudentsItem(

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("major")
    val major: String? = null,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("gpa")
    val gpa: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("age")
    val age: String? = null
)
