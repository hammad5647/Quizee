package com.example.quizee.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizee.data.model.QuizQuesModel
import com.example.quizee.domain.DataRepo.Companion.repository
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private var x = mutableListOf<QuizQuesModel>()

    private var questData = MutableLiveData<MutableList<QuizQuesModel>>()
    var quest: LiveData<MutableList<QuizQuesModel>> = questData

    var selectedOption : String? = null

    var totalRightAnswer : Int = 0
    var totalWrongAnswer : Int = 0

    private var _questNumber = MutableLiveData(0)
    var questNumber : LiveData<Int> = _questNumber

    var category = ""

    fun getData() {
        viewModelScope.launch {
            val questList = repository.getQuizQuestion(category = category)
            for (i in questList?.results!!) {
                val options: MutableList<String?>? = i!!.incorrectAnswers
                options!!.add(i.correctAnswer)
                options.shuffle()
                x.add(QuizQuesModel(i.question!!, options, i.correctAnswer!!))

                questData.value = x
                Log.e("QUESTIONS", "getData: ===========${questData.value} ")
            }
        }
    }

    fun nextQuiz() {
        if (_questNumber.value!! < 9){
            rightAnswer()
            _questNumber.value = _questNumber.value!! + 1
            Log.e("selected", "nextQuiz: $selectedOption", )
            Log.e("Correct Answer", "nextQuiz: ${quest.value?.get(_questNumber.value!!)?.answer}", )
        }else{
            rightAnswer()
        }
    }
    private fun rightAnswer(){
        if (quest.value?.get(_questNumber.value!!)?.answer == selectedOption){
            totalRightAnswer ++
        }else{
            totalWrongAnswer++
        }
    }
}