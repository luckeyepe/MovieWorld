package com.example.morkince.movieworld.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.morkince.movieworld.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView_mainSignUp.setOnClickListener {
            Log.d("INFO", "Sign Up text in Main Activity Selected")
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        button_mainLogIn.setOnClickListener {
            Log.d("INFO", "Log In button in Main Activity Selected")
            if(isCompleteData()){
                var email = editText_mainEmail.text.toString().trim()
                var password = editText_mainPassword.text.toString().trim()
                signInUser(email, password)
            }else{
                Log.e("ERROR", "Incomplete data")
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please enter all user details")
                alertDialog.setTitle("INFO MISSING")
                alertDialog.show()
            }
        }

    }

    private fun signInUser(email: String, password: String) {

        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please check your email address and/or password")
                alertDialog.setTitle("INCORRECT CREDENTIALS")
                alertDialog.show()
            }
    }

    private fun isCompleteData(): Boolean {
        return !editText_mainEmail.text.isNullOrEmpty() && !editText_mainPassword.text.isNullOrEmpty()
    }

    public override fun onStart() {
        super.onStart()
        val mAuth = FirebaseAuth.getInstance()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        startActivity(Intent(this, HomeScreenActivity::class.java))
        finish()
    }
}
