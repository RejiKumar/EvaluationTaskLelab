package com.example.evaluationexerciselelab.ui

import MainViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluationexerciselelab.R
import com.example.evaluationexerciselelab.data.api.ApiHelperImpl
import com.example.evaluationexerciselelab.data.api.RetrofitBuilder
import com.example.evaluationexerciselelab.data.local.DatabaseBuilder
import com.example.evaluationexerciselelab.data.local.DatabaseHelperImpl
import com.example.evaluationexerciselelab.data.local.entity.Student
import com.example.evaluationexerciselelab.data.model.ApiStudentsItem
import com.example.evaluationexerciselelab.ui.base.ApiStudentAdapter
import com.example.evaluationexerciselelab.ui.base.UiState
import com.example.evaluationexerciselelab.ui.base.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ApiStudentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ApiStudentAdapter(arrayListOf()) { student ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("student_id", student.id)
            intent.putExtra("student_firstName", student.firstName)
            intent.putExtra("student_lastName", student.lastName)
            intent.putExtra("student_age", student.age)
            intent.putExtra("student_gender", student.gender)
            intent.putExtra("student_major", student.major)
            intent.putExtra("student_gpa", student.gpa)
            startActivity(intent)
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        progressBar = findViewById(R.id.progressBar)
        viewModel.getUiState().observe(this) {
            when (it) {
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    renderList(it.data)
                    recyclerView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                is UiState.Error -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(students: List<ApiStudentsItem>) {
        adapter.addData(students)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        )[MainViewModel::class.java]
    }
}
