package com.example.nourishpath.data.api.nourishpath.response

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("article")
	val article: Article
)