package com.example.morkince.movieworld

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.morkince.movieworld.activities.SignUpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView_mainSignUp.setOnClickListener {
            Log.d("INFO", "Sign Up text in Main Activity Selected")
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
