package com.example.evaluationexerciselelab.ui.base

import MainViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evaluationexerciselelab.data.api.ApiHelper
import com.example.evaluationexerciselelab.data.local.DatabaseHelper

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper, private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiHelper, dbHelper, context) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}