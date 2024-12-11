package com.example.nourishpath.ui.childinput

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.nourishpath.ApiConfig
import com.example.nourishpath.data.api.nourishpath.request.CheckStuntingRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class ChildInputViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _stuntingStatus = MutableLiveData<String>()
    val stuntingStatus: LiveData<String> = _stuntingStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun checkStuntingAndSaveData(usia: Int, tinggi: Float, berat: Float) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                Log.d(
                    "ChildInputViewModel",
                    "Sending request for stunting check: age = $usia, weight = $berat, height = $tinggi"
                )

                val request = CheckStuntingRequest(age = usia, weight = berat, height = tinggi)
                val response = ApiConfig.getCheckStunting().checkStunting(request)

                Log.d("ChildInputViewModel", "Received response: ${response.stuntingStatus}")
                val prediction = if (response.stuntingStatus == "stunting") 1 else 0

                _stuntingStatus.value = response.stuntingStatus

                saveChildInputData(usia, berat, tinggi, prediction)

            } catch (e: SocketTimeoutException) {
                _errorMessage.value = "Timeout, please try again."
                Log.e("ChildInputViewModel", "Timeout error: ${e.message}")
            } catch (e: IOException) {
                _errorMessage.value = "Network error, please check your connection."
                Log.e("ChildInputViewModel", "Network error: ${e.message}")
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred, please try again."
                Log.e("ChildInputViewModel", "General error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveChildInputData(age: Int, weight: Float, height: Float, prediction: Int) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            Log.d("ChildInputViewModel", "User ID: $userId")

            val data = hashMapOf(
                "userId" to userId,
                "age" to age,
                "weight" to weight,
                "height" to height,
                "prediction" to prediction,
                "timestamp" to FieldValue.serverTimestamp()
            )

            Log.d("ChildInputViewModel", "Saving data to Firestore: $data")

            firestore.collection("stunting_checks")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "Data successfully saved with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error saving data", e)
                }
        } else {
            Log.e("ChildInputViewModel", "User not logged in!")
        }
    }
}