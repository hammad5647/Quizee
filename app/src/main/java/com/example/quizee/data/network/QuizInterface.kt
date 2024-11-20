package com.example.quizee.data.network

import com.example.quizee.data.helper.model.QuizDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizInterface {

    @GET("/api.php")
    fun getApi(
        @Query("amount") amount: Int = 10,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String = "easy",
        @Query("type") type: String = "multiple"
    ):Call<QuizDataModel>
}