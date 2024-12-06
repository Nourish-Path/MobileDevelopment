package com.example.nourishpath.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.nourishpath.ApiConfig
import com.example.nourishpath.data.api.nourishpath.response.Article
import kotlinx.coroutines.launch

class DetailViewModel(private val id: Int): ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> = _article

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        showDetailedData(id)
    }

    private fun showDetailedData(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getDetailArticle(id)
                _article.value = response.article
                Log.d("DetailActivity", "EXECUTED")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}