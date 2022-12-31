package com.chrisprojects.mychatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var mDBRef: DatabaseReference

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.email_edit)
        editPassword = findViewById(R.id.pass_edit)
        editName = findViewById(R.id.name_edit)
        buttonSignUp = findViewById(R.id.signup_btn)

        buttonSignUp.setOnClickListener{
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            
            signUp(name,email, password)
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful )
                {
                    addUserToDataBase(name, email, mAuth.currentUser?.uid!!)
                    Toast.makeText(this@SignUp,"Signup Successful!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else{
                    if (task.getException() is FirebaseAuthUserCollisionException) {

                        Toast.makeText(this@SignUp,"Provided Email Is Already Registered!", Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(this@SignUp, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun addUserToDataBase(name: String, email: String, uid: String) {
        mDBRef = FirebaseDatabase.getInstance().getReference()

        mDBRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}