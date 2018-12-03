package com.example.morkince.movieworld.activities

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.fragments.BranchFragment
import com.example.morkince.movieworld.fragments.HomeFragment
import com.example.morkince.movieworld.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_profile.*

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        title = "Home"
        //show home fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_homeScreenFrame, HomeFragment())
            commit()
        }


        //load up fragments
        navigation_homeScreenBottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){

                R.id.navigation_home ->{
                    //fixes empty activity bug
                    startActivity(Intent(this, HomeScreenActivity::class.java))
                    finish()
                    //starts activity without animations
                    overridePendingTransition(0, 0);
                    true
                }

                R.id.navigation_profile ->{
                    title = "Profile"
                    //show profile fragment
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout_homeScreenFrame, ProfileFragment())
                        commit()
                    }
                    true

                }

                R.id.navigation_branch -> {
                    title = "Cinema Branches"
                    //show branches fragment
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout_homeScreenFrame, BranchFragment())
                        commit()
                    }
                    true
                }

                else -> false
            }
        }
    }
}
