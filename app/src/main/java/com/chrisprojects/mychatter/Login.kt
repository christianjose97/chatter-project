package com.chrisprojects.mychatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var buttonLogIn: Button
    private lateinit var buttonSignUp: Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.email_edit)
        editPassword = findViewById(R.id.pass_edit)
        buttonLogIn = findViewById(R.id.login_btn)
        buttonSignUp = findViewById(R.id.signup_btn)

        buttonSignUp.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        buttonLogIn.setOnClickListener{
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            login(email, password)
        }

    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@Login,"Login Successful", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login, "User does not Exist", Toast.LENGTH_SHORT).show()

                }
            }
    }
}