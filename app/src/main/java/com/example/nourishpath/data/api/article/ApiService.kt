package com.example.nourishpath.data.api.article

import com.example.nourishpath.data.api.article.model.DetailArticleResponse
import com.example.nourishpath.data.api.article.model.ListArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): ListArticleResponse

    @GET("articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int
    ): DetailArticleResponse
}