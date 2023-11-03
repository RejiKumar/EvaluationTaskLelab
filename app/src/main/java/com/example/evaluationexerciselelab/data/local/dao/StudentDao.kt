package com.example.evaluationexerciselelab.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.evaluationexerciselelab.data.local.entity.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    suspend fun getAll(): List<Student>

    @Insert
    suspend fun insertAll(students: List<Student>)

    @Delete
    suspend fun delete(student: Student)

}