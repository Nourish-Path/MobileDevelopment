package com.example.nourishpath.ui.childinput

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.nourishpath.ApiConfig
import com.example.nourishpath.data.api.nourishpath.request.CheckStuntingRequest
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class ChildInputViewModel: ViewModel() {
    private val _stuntingStatus = MutableLiveData<String>()
    val stuntingStatus: LiveData<String> = _stuntingStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun checkStunting(usia: Int, tinggi: Float, berat: Float) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val request = CheckStuntingRequest(age = usia, weight = berat, height = tinggi)
                val response = ApiConfig.getCheckStunting().checkStunting(request)
                _stuntingStatus.value = response.stuntingStatus
            }  catch (e: SocketTimeoutException) {
                _errorMessage.value = "Timeout, please try again."
            } catch (e: IOException) {
                _errorMessage.value = "Network error, please check your connection."
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred, please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}