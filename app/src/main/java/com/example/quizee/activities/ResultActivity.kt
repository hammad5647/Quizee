package com.example.quizee.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizee.MainActivity
import com.example.quizee.R
import com.example.quizee.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent
        val totalRightAnswer = intent.getIntExtra("totalRightAnswer",0)
        val incorrectAnswers = intent.getIntExtra("totalWrongAnswer",0)

        binding.rightAnswer.text = totalRightAnswer.toString()
        binding.wrongAnswer.text = incorrectAnswers.toString()
        binding.restartBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.restartImage.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }
}