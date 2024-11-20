package com.example.quizee.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizee.R
import com.example.quizee.databinding.ActivityGamePlayBinding
import com.example.quizee.viewModel.QuizViewModel

class GamePlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamePlayBinding
    private lateinit var countDown: CountDownTimer
    private val viewModel by viewModels<QuizViewModel>()
    lateinit var dialog: Dialog

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGamePlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observer()
        getIntData()
        clickInit()
        timer()
    }

    @SuppressLint("SetTextI18n")
    fun observer() {
        viewModel.quest.observe(this) {
            if (it != null) {
                binding.questTxt.text = it[0].question
                binding.txtOptA.text = it[0].options[0]
                binding.txtOptB.text = it[0].options[1]
                binding.txtOptC.text = it[0].options[2]
                binding.txtOptD.text = it[0].options[3]
                countDown.start()
            }
        }
        viewModel.questNumber.observe(this) {
            binding.questTxt.text = viewModel.quest.value?.get(it)?.question
            binding.txtOptA.text = viewModel.quest.value?.get(it)?.options?.get(0)
            binding.txtOptB.text = viewModel.quest.value?.get(it)?.options?.get(1)
            binding.txtOptC.text = viewModel.quest.value?.get(it)?.options?.get(2)
            binding.txtOptD.text = viewModel.quest.value?.get(it)?.options?.get(3)

            binding.nextQuizBtn.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickInit() {
        binding.optionALayout.setOnClickListener {
            binding.optionALayout.setBackgroundResource(R.drawable.selected_opt)
            binding.optionBLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionCLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionDLayout.setBackgroundResource(R.drawable.option_bg)
            viewModel.selectedOption = viewModel.questNumber.value?.let { it1 ->
                viewModel.quest.value?.get(
                    it1
                )?.options?.get(0)
            }
            binding.nextQuizBtn.visibility = View.VISIBLE
        }
        binding.optionBLayout.setOnClickListener {
            binding.optionBLayout.setBackgroundResource(R.drawable.selected_opt)
            binding.optionALayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionCLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionDLayout.setBackgroundResource(R.drawable.option_bg)
            viewModel.selectedOption = viewModel.questNumber.value?.let { it1 ->
                viewModel.quest.value?.get(
                    it1
                )?.options?.get(1)
            }
            binding.nextQuizBtn.visibility = View.VISIBLE

        }
        binding.optionCLayout.setOnClickListener {
            binding.optionCLayout.setBackgroundResource(R.drawable.selected_opt)
            binding.optionALayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionBLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionDLayout.setBackgroundResource(R.drawable.option_bg)
            viewModel.selectedOption = viewModel.questNumber.value?.let { it1 ->
                viewModel.quest.value?.get(
                    it1
                )?.options?.get(2)
            }
            binding.nextQuizBtn.visibility = View.VISIBLE

        }
        binding.optionDLayout.setOnClickListener {
            binding.optionDLayout.setBackgroundResource(R.drawable.selected_opt)
            binding.optionALayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionBLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionCLayout.setBackgroundResource(R.drawable.option_bg)
            viewModel.selectedOption = viewModel.questNumber.value?.let { it1 ->
                viewModel.quest.value?.get(
                    it1
                )?.options?.get(3)
            }
            binding.nextQuizBtn.visibility = View.VISIBLE
        }
        binding.nextQuizBtn.setOnClickListener {
            viewModel.nextQuiz()
            binding.optionALayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionBLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionCLayout.setBackgroundResource(R.drawable.option_bg)
            binding.optionDLayout.setBackgroundResource(R.drawable.option_bg)
            binding.nextQuizBtn.visibility = View.GONE
            binding.questNumberTxt.text = "${viewModel.questNumber.value!! + 1}/10"
            if (viewModel.questNumber.value == 9){
                binding.nextQuizBtn.visibility = View.GONE
                binding.submitBtn.visibility = View.VISIBLE
                viewModel.nextQuiz()

            }
            countDown.cancel()
            timer()
        }
        binding.submitBtn.setOnClickListener{
            var intent = Intent(this, ResultActivity::class.java)
            Log.e("RightAnswers", "clickInit: ${viewModel.totalRightAnswer}", )
            Log.e("WrongAnswers", "clickInit: ${viewModel.totalWrongAnswer}", )
            intent.putExtra("totalRightAnswer", viewModel.totalRightAnswer)
            intent.putExtra("totalWrongAnswer", viewModel.totalWrongAnswer)
            startActivity(intent)

        }
    }

    private fun getIntData() {
        val intent = intent
        val category = intent.getStringExtra("category")
        viewModel.category = category!!
        viewModel.getData()
    }

    private fun timer() {
        countDown = object : CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.quizSeconds.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.quizSeconds.text = "0"
                viewModel.nextQuiz()
                countDown.cancel()
                countDown.start()
            }
        }.start()
    }
}