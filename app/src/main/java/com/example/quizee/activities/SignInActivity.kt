@file:Suppress("DEPRECATION")

package com.example.quizee.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizee.MainActivity
import com.example.quizee.R
import com.example.quizee.data.helper.AuthHelper.Companion.authHelper
import com.example.quizee.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickInit()
//        googleClient()
//        val googleRegister =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
//                val googleId = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//                val credential = GoogleAuthProvider.getCredential(googleId.result.idToken, null)
//
//                FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener {
//                    authHelper.currentUser()
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                }.addOnFailureListener {
//                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        binding.googleBtn.setOnClickListener {
//            val googleIntent = googleClient.signInIntent
//            googleRegister.launch(googleIntent)
//        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun clickInit() {
        binding.createNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.signInBtn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordTxt.text.toString()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Please enter email"
                binding.emailInputLayout.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Please enter password"
                binding.passwordInputLayout.requestFocus()
                return@setOnClickListener
            } else {
                binding.emailInputLayout.error = null
                binding.passwordInputLayout.error = null

                GlobalScope.launch {
                    val message = authHelper.signIn(email, password)
                    withContext(Dispatchers.Main) {
                        if (message == "Success") {
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@SignInActivity, "${authHelper.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
//
//    private fun googleClient() {
//        val googleSignOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(R.string.id_token.toString()).requestEmail().build()
//        googleClient =GoogleSignIn.getClient(this, googleSignOption)
//    }
}
