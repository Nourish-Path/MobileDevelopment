package com.example.nourishpath.data.api.nourishpath

import com.example.nourishpath.data.api.nourishpath.request.CheckStuntingRequest
import com.example.nourishpath.data.api.nourishpath.request.NutrientRequest
import com.example.nourishpath.data.api.nourishpath.response.CheckStuntingResponse
import com.example.nourishpath.data.api.nourishpath.response.DetailArticleResponse
import com.example.nourishpath.data.api.nourishpath.response.ListArticleResponse
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): ListArticleResponse

    @GET("articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int
    ): DetailArticleResponse

    @POST("check_stunting/")
    suspend fun checkStunting(
        @Body request: CheckStuntingRequest
    ): CheckStuntingResponse

    @POST("recommend")
    suspend fun getRecommendation(
        @Body request: NutrientRequest
    ): RecommendationResponse
}