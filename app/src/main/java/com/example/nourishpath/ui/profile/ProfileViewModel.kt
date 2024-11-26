package com.example.nourishpath.ui.profile

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.local.Profile
import com.example.nourishpath.data.local.ProfileDao
import com.example.nourishpath.data.local.ProfileRoomDatabase
import kotlinx.coroutines.launch


class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val profileDao = ProfileRoomDatabase.getInstance(application).profileDao()
    private val _profileLiveData = MutableLiveData<Profile?>()
    val profileLiveData: LiveData<Profile?> get() = _profileLiveData

    fun loadProfile() {
        viewModelScope.launch {
            val profile = profileDao.getProfile()
            // Konversi String ke Uri
            profile?.let {
                _profileLiveData.value = it.copy(profileImageUri = Uri.parse(it.profileImageUri).toString())
            }
        }
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            profileDao.deleteAll()
            profileDao.insert(profile)
            _profileLiveData.value = profile
            Toast.makeText(getApplication(), "Profile berhasil disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}
