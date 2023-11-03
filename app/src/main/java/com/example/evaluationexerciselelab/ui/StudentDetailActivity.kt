package com.example.evaluationexerciselelab.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.widget.AppCompatTextView
import com.example.evaluationexerciselelab.R

class StudentDetailActivity : AppCompatActivity() {

    private var TAG = "StudentDetailActivity"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_student_detail)

        val studentID = intent.getStringExtra("student_id")
        val studentFirstName = intent.getStringExtra("student_firstName")
        val studentLastName = intent.getStringExtra("student_lastName")
        val studentAge = intent.getStringExtra("student_age")
        val studentGender = intent.getStringExtra("student_gender")
        val studentMajor = intent.getStringExtra("student_major")
        val studentGPA = intent.getStringExtra("student_gpa")

        val tvStudentID = findViewById<AppCompatTextView>(R.id.tvStudentID)
        val tvStudentFullName = findViewById<AppCompatTextView>(R.id.tvStudentFullName)
        val tvStudentAge = findViewById<AppCompatTextView>(R.id.tvStudentAge)
        val tvStudentGender = findViewById<AppCompatTextView>(R.id.tvStudentGender)
        val tvStudentMajor = findViewById<AppCompatTextView>(R.id.tvStudentMajor)
        val tvStudentGPA = findViewById<AppCompatTextView>(R.id.tvStudentGPA)

        tvStudentID.text = "ID is $studentID"
        tvStudentFullName.text = "Name is $studentFirstName $studentLastName"
        tvStudentAge.text = "Age is $studentAge"
        tvStudentGender.text = "Gender is $studentGender"
        tvStudentMajor.text = "Major is $studentMajor"
        tvStudentGPA.text = "GPA is $studentGPA"

    }
}