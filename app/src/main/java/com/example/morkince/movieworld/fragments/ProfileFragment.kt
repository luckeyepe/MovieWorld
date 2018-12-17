package com.example.morkince.movieworld.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.activities.HomeScreenActivity
import com.example.morkince.movieworld.activities.MainActivity
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        view.apply {

            this.button_profileFragmentLogOut.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                activity!!.finish()

                LoginManager.getInstance().logOut()//logout facebook account
                startActivity(Intent(context, MainActivity::class.java))
                activity!!.finish()
            }


            //get user info
            var currentUser = FirebaseAuth.getInstance().currentUser

            FirebaseFirestore.getInstance().collection("Users").document(currentUser!!.uid)
                .get()
                .addOnCompleteListener {
                    task: Task<DocumentSnapshot> ->
                    if (task.isSuccessful){
                        var document = task.result
                        if (document!!.exists()) {
                            var user = document.toObject(com.example.morkince.movieworld.models.User::class.java)
                            this.textView_profileFragmentEmailAddress.text = user!!.user_email
                            this.editText_profileFragmentFirstName.setText(user.user_first_name)
                            this.editText_profileFragmentLastName.setText(user.user_last_name)
                            this.editText_profileFragmentBirthdate.setText(user.user_birth_date.toString())
                        }
                    }else{
                        //Error during pulling the data from fire store
                        Log.e("Error in Checking", task.exception.toString())
                    }
                }
        }

        return view
    }


}
