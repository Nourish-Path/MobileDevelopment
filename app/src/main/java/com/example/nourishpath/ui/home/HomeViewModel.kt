package com.example.nourishpath.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.local.Profile
import com.example.nourishpath.data.local.ProfileDao
import com.example.nourishpath.data.local.ProfileRoomDatabase
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val profileDao: ProfileDao =
        ProfileRoomDatabase.getInstance(application).profileDao()

    private val _profile = MutableLiveData<Profile?>()
    val profile: LiveData<Profile?> = _profile

    fun fetchProfile() {
        viewModelScope.launch {
            _profile.value = profileDao.getProfile()
        }
    }
}