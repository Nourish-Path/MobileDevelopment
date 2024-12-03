package com.example.nourishpath.data.api.article.response

import com.google.gson.annotations.SerializedName

data class ListArticleResponse(

	@field:SerializedName("listArticle")
	val listArticle: List<Article>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Article(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String
)
