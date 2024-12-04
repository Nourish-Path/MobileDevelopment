package com.example.nourishpath.ui.guides

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.article.ApiConfig
import com.example.nourishpath.data.api.article.response.Article
import kotlinx.coroutines.launch

class GuidesViewModel : ViewModel() {
    private val _listArticle = MutableLiveData<List<Article>>()
    val listArticle: LiveData<List<Article>> = _listArticle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        showArticles()
    }

    private fun showArticles() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getArticles()
                _listArticle.value = response.listArticle
                Log.d("GuidesViewModel", "EXECUTED")
            } catch (e: Exception) {
                e.printStackTrace() // Log error
            } finally {
                _isLoading.value = false
            }
        }
    }
}