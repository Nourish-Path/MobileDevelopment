package com.example.nourishpath.data.api.article

import com.example.nourishpath.data.api.article.model.ListArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/.")
    suspend fun getArticles(): ListArticleResponse
}