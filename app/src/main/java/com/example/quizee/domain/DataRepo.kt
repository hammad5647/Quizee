package com.example.quizee.domain

import com.example.quizee.data.helper.ApiHelper

class DataRepo {
    companion object {
        var repository = DataRepo()
    }
    private var helper = ApiHelper()
    suspend fun getQuizQuestion(category: String) = helper.getApi(category)
}