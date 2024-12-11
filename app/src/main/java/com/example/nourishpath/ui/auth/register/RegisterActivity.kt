package com.example.nourishpath.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nourishpath.databinding.ActivityRegisterBinding
import com.example.nourishpath.ui.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val loginText = SpannableString("Already have an account? Login!")
        loginText.setSpan(UnderlineSpan(), loginText.length - 6, loginText.length, 0)
        binding.tvLogin.text = loginText

        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            if (validateInput(name, email, password)) {
                binding.RegisterprogressBar.visibility = View.VISIBLE
                registerUser(email, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        when {
            name.isEmpty() -> {
                binding.edRegisterName.error = "Name should be filled"
                return false
            }
            email.isEmpty() -> {
                binding.edRegisterEmail.error = "Email should be filled"
                return false
            }
            password.isEmpty() -> {
                binding.edRegisterPassword.error = "Password should be filled"
                return false
            }
            password.length < 6 -> {
                binding.edRegisterPassword.error = "Password must have at least 6 characters"
                return false
            }
            else -> return true
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                binding.RegisterprogressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Succeess!", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(
                        this,
                        "Register Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
