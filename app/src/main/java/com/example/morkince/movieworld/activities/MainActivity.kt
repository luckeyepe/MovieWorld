package com.example.morkince.movieworld.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.example.morkince.movieworld.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth ?= null
    var user: FirebaseUser ?= null
    var mAuthListener: FirebaseAuth.AuthStateListener ?= null
    private lateinit var googleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        //check if user is already logged in
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            firebaseAuth: FirebaseAuth ->
            user = firebaseAuth.currentUser
            if (user!= null){
                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()
            }
        }

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

        button_mainGoogleLogIn.setOnClickListener {

            signInUsingGoogleAccount()
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
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null){
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    private fun signInUsingGoogleAccount() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("ERROR", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("INFO", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("INFO", "signInWithCredential:success")
                    var userMap =  HashMap<String, String>()
                    userMap["user_email"] = user!!.email.toString()
                    userMap["user_name"] = user!!.displayName.toString()

                    //add user info to firestore
                    FirebaseFirestore.getInstance().collection("Users").document(user!!.uid)
                        .set(userMap as Map<String, Any>)
                        .addOnCompleteListener {
                            task: Task<Void> ->
                            if (task.isSuccessful){
                                startActivity(Intent(this, HomeScreenActivity::class.java))
                                finish()
                            }else{
                                //show error
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Error", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
