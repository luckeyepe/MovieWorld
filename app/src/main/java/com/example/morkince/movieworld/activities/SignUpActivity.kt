package com.example.morkince.movieworld.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    var mFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        title = "SIGN UP"//change text on action bar

        button_signUpCreateAccount.setOnClickListener {
            Log.d("INFO", "Create Account button in SignUp activity selected")
            if (isCompleteData()) {
                if (isValidPassword()) {
                    var email = editText_signUpEmail.text.toString().trim()
                    var firstName = editText_signUpFirstName.text.toString().trim()
                    var lastName = editText_signUpLastName.text.toString().trim()
                    var password = editText_signUpPassword.text.toString().trim()

                    createAccount(email, firstName, lastName, password)
                }
            } else {
                Log.e("ERROR", "Incomplete data")
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("Please enter all user details")
                alertDialog.setTitle("INFO MISSING")
                alertDialog.show()
            }
        }
    }

    private fun isValidPassword(): Boolean {
        var password = editText_signUpPassword.text.toString().trim()
        var password2 = editText_signUpConfirmPassword.text.toString().trim()

        if(isStringContainNumber(password)){
            if(isStringContainUpperCase(password)){
                if(isStringContainLowerCase(password)){
                    if(isStringContainSpecialCharacter(password)){
                        if(password.length >=8){
                            if (password == password2){
                                return true
                            }else{
                                Log.e("ERROR", "Incomplete data")
                                var alertDialog = AlertDialog.Builder(this)
                                alertDialog.setMessage("Please matching password")
                                alertDialog.setTitle("PASSWORD MISMATCH")
                                alertDialog.show()
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    private fun isStringContainSpecialCharacter(string: String): Boolean {
        for(c in string.toCharArray()){
            if (!c.isLetterOrDigit())
                return true
        }

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) special character")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()

        return false
    }

    private fun isStringContainLowerCase(string: String): Boolean {
        for(c in string.toCharArray()){
            if (c.isLowerCase())
                return true
        }

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) lower case character")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()
        return false
    }

    private fun isStringContainUpperCase(string: String): Boolean {
        for(c in string.toCharArray()){
            if (c.isUpperCase())
                return true
        }
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) upper case character")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()

        return false
    }

    private fun isStringContainNumber(string: String): Boolean {

        for(c in string.toCharArray()){
            if (c.isDigit())
                return true
        }

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Password must contain at least one (1) digit")
        alertDialog.setTitle("INCORRECT PASSWORD")
        alertDialog.show()

        return false
    }


    private fun isCompleteData(): Boolean {
            return !editText_signUpEmail.text.isNullOrEmpty() && !editText_signUpLastName.text.isNullOrEmpty()
                    && !editText_signUpPassword.text.isNullOrEmpty() && !editText_signUpConfirmPassword.text.isNullOrEmpty()
        }

        fun createAccount(email: String, firstName: String, lastName: String, password: String) {

            var alertDialog = AlertDialog.Builder(this)

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    var user = mAuth.currentUser
                    var mFirestore = FirebaseFirestore.getInstance().collection("Users").document("${user!!.uid}")

                    var userMap = User()
                    userMap.user_email = user!!.email.toString()
                    userMap.user_first_name = firstName
                    userMap.user_last_name = lastName

                    mFirestore.set(userMap)
                        .addOnSuccessListener {
                            Log.d("INFO", "Created user: ${user!!.email}")
                            alertDialog.setMessage("Your are now signed up")
                            alertDialog.setTitle("Sign Up Successful")
                            alertDialog.setCancelable(false)
                            alertDialog.setPositiveButton("OK") { dialog, _ ->
                                run {
                                    dialog.dismiss()
                                    //Proceed to home screen
                                    startActivity(Intent(this, HomeScreenActivity::class.java))
                                    finish()
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
