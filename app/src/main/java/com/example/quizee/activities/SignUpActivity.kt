package com.example.quizee.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizee.R
import com.example.quizee.databinding.ActivitySignUpBinding
import com.example.quizee.data.helper.AuthHelper.Companion.authHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickInit()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun clickInit() {
        binding.alreadyHaveAccountBtn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signUpBtn.setOnClickListener {
            var email = binding.emailText.text.toString()
            var password = binding.passwordTxt.text.toString()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Email cannot be empty"
                binding.emailInputLayout.requestFocus()
            } else if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Password cannot be empty"
                binding.passwordInputLayout.requestFocus()
            } else {
                binding.emailInputLayout.error = null
                binding.passwordInputLayout.error = null

                GlobalScope.launch {
                    val message = authHelper.signUp(email, password)
                    withContext(Dispatchers.Main) {

                        if (message == "Success") {
                            Log.e("signUpSuccess", "clickInit: ")
                            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this@SignUpActivity, "${authHelper.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}