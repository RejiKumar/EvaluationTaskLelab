package com.example.evaluationexerciselelab.data.local.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "age") val age: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "major") val major: String?,
    @ColumnInfo(name = "gpa") val gpa: String?
)