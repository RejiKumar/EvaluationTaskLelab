package com.example.evaluationexerciselelab.ui

import MainViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ApiStudentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var TAG = "MainActivity"
    var studentsList: ArrayList<ApiStudentsItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupObserver() {
        progressBar = findViewById(R.id.progressBar)
        viewModel.getUiState().observe(this) {
            when (it) {
                is UiState.Success -> {
                    networkDialog(show = false)
                    progressBar.visibility = View.GONE
                    renderList(it.data)
                    recyclerView.visibility = View.VISIBLE
                }

                is UiState.Offline -> {
                    networkDialog(show = true)
                    progressBar.visibility = View.GONE
                    renderList(it.data)
                    recyclerView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    networkDialog(show = false)
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                is UiState.Error -> {
                    networkDialog(show = false)
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
        studentsList.addAll(students)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)),
                applicationContext
            )
        )[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filter(newText)
                }
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<ApiStudentsItem> = ArrayList()

        for (item in studentsList) {
            val name = item.firstName + " " + item.lastName
            if (name.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Students Found.", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredList)
        }
    }

    private fun networkDialog(show: Boolean) {
        if (!show) {
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Check your internet connection")
            .setTitle("No Internet Connection");
        builder.setNeutralButton(R.string.ok, DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        builder.create().show()
    }
}
