package com.example.quizee.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizee.R
import com.example.quizee.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private var category: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickInit()
    }

    private fun clickInit() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.categoryBtn1.setOnClickListener {

            binding.categoryBtn1.setBackgroundResource(R.drawable.selected_opt)
            binding.categoryBtn2.setBackgroundResource(R.drawable.option_bg)
            category = "27"
            binding.categoryOkBtn.visibility = View.VISIBLE

        }
        binding.categoryBtn2.setOnClickListener {
            binding.categoryBtn2.setBackgroundResource(R.drawable.selected_opt)
            binding.categoryBtn1.setBackgroundResource(R.drawable.option_bg)
            category = "21"
            binding.categoryOkBtn.visibility = View.VISIBLE

        }
        binding.categoryOkBtn.setOnClickListener {
            val intent = Intent(this, GamePlayActivity::class.java)
            intent.putExtra("category", category)
            startActivity(intent)
            finish()
        }
    }
}