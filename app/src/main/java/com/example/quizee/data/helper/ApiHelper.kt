package com.example.quizee.data.helper

import android.util.Log
import com.example.quizee.data.helper.model.QuizDataModel
import com.example.quizee.data.network.QuizInterface
import com.example.quizee.data.network.RetroClient.Companion.getQuiz
import retrofit2.awaitResponse

class ApiHelper {
    suspend fun getApi(category: String): QuizDataModel? {
        val quizInterface: QuizInterface = getQuiz().create(QuizInterface::class.java)
        val response = quizInterface.getApi(category = category).awaitResponse()
        if (response.isSuccessful) {
            Log.e("Success", "getApi:${response.body()} ")
            return response.body()
        }
        return null
    }
}