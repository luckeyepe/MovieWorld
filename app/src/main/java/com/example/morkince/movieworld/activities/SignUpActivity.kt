package com.example.morkince.movieworld.activities

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.morkince.movieworld.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.opencensus.tags.Tag
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    var mFirestore: FirebaseFirestore ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        title = "SIGN UP"//change text on action bar

        button_signUpCreateAccount.setOnClickListener {
            Log.d("INFO", "Create Account button in SignUp activity selected")
            if(isCompleteData()){
                var email = editText_signUpEmail.text.toString().trim()
                var name = editText_signUpName.text.toString().trim()
                var password = editText_signUpPassword.text.toString().trim()

                createAccount(email, name, password)
            }else{
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please enter all user details")
                alertDialog.setTitle("INFO MISSING")
                alertDialog.show()
            }
        }
    }

    private fun isCompleteData(): Boolean {
        return !editText_signUpEmail.text.isNullOrEmpty() && !editText_signUpName.text.isNullOrEmpty()
                && !editText_signUpPassword.text.isNullOrEmpty()
    }

    fun createAccount(email: String, name: String, password: String){

        var alertDialog = AlertDialog.Builder(this)

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                var user = mAuth.currentUser
                var mFirestore = FirebaseFirestore.getInstance().collection("Users").document("${user!!.uid}")

                var userMap = HashMap<String, Any>()
                userMap["user_email"] = user!!.email.toString()
                userMap["user_name"] = name

                mFirestore.set(userMap)
                    .addOnSuccessListener {
                        Log.d("INFO", "Created user: ${user!!.email}")
                        alertDialog.setMessage("Your are now signed up")
                        alertDialog.setTitle("Sign Up Successful")
                        alertDialog.setCancelable(false)
                        alertDialog.setPositiveButton("OK") { dialog, _ ->
                            run {
                                dialog.dismiss()
                                //Proceed to dashboard
                            }
                        }
                        alertDialog.show()
                    }
                    .addOnFailureListener {
                        Log.e("ERROR", "Could not create user: $email")
                        alertDialog.setMessage("Your are not signed up")
                        alertDialog.setTitle("Sign Up Failed")
                        alertDialog.show()
                    }
            }
            .addOnFailureListener {
                Log.e("ERROR", "Could not create user: $email")
                alertDialog.setMessage("Your are not signed up")
                alertDialog.setTitle("Sign Up Failed")
                alertDialog.show()
            }
    }
}
