package com.example.nourishpath.data.api.article

import com.example.nourishpath.data.api.article.request.CheckStuntingRequest
import com.example.nourishpath.data.api.article.response.CheckStuntingResponse
import com.example.nourishpath.data.api.article.response.DetailArticleResponse
import com.example.nourishpath.data.api.article.response.ListArticleResponse
import com.example.nourishpath.data.api.article.response.RecommendationResponse
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

//    @POST("check_stunting")
//    suspend fun checkStunting(
//        @Body usia: Int,
//        @Body tinggiBadan: Float,
//        @Body beratBadan: Float,
//    ): CheckStuntingResponse

    @POST("check_stunting/")
    suspend fun checkStunting(
        @Body request: CheckStuntingRequest
    ): CheckStuntingResponse

    @FormUrlEncoded
    @POST("recommendation")
    suspend fun getRecommendation(
        @Field("age") age: Int,
        @Field("foods") foods: String,
        @Field("descriptions") descriptions: String
    ): RecommendationResponse
}