package com.example.myssenger.registerlogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.myssenger.R
import com.example.myssenger.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            if (performLogin()) return@setOnClickListener
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }

    private fun performLogin(): Boolean {
        val email = email_edittext_login.text.toString()
        val password = edittext_password_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter your email/password", Toast.LENGTH_SHORT).show()
            return true
        }

        Log.d("Login", "Attempt login with email/password: $email/***")

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("Login", "Successfully logged in as user: ${it.result?.user?.email}")

                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Login", "Failed to login: ${it.message}")

                Toast.makeText(this, "Failed to login: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        return false
    }
}