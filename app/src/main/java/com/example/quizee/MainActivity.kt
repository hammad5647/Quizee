package com.example.quizee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizee.activities.CategoryActivity
import com.example.quizee.activities.SignInActivity
import com.example.quizee.databinding.ActivityMainBinding
import com.example.quizee.data.helper.AuthHelper.Companion.authHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.startGameBtn.setOnClickListener{
            startActivity(Intent(this, CategoryActivity::class.java))

        }
        binding.logoutBtn.setOnClickListener{
            authHelper.logOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}