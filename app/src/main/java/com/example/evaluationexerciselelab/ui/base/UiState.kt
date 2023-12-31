package com.example.evaluationexerciselelab.ui.base

sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>

    data class Offline<T>(val data: T) : UiState<T>

    object Loading : UiState<Nothing>

}