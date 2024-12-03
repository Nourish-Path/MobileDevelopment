package com.example.nourishpath.ui.childinput

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.article.ApiConfig
import com.example.nourishpath.data.api.article.request.CheckStuntingRequest
import kotlinx.coroutines.launch

class ChildInputViewModel: ViewModel() {
    private val _stuntingStatus = MutableLiveData<String>()
    val stuntingStatus: LiveData<String> = _stuntingStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun checkStunting(usia: Int, tinggi: Float, berat: Float) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val request = CheckStuntingRequest(age = usia, weight = berat, height = tinggi)
                val response = ApiConfig.getCheckStunting().checkStunting(request)
                _stuntingStatus.value = response.stuntingStatus
            } catch (e: Exception) {
                Log.d("ChildInputViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}