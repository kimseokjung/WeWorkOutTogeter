package com.example.weworkouttogether.fragments.login

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import com.example.weworkouttogether.LogInActivity
import com.example.weworkouttogether.MainActivity
import com.example.weworkouttogether.R
import com.example.weworkouttogether.Storage
import com.example.weworkouttogether.databinding.FragmentLoginBinding
import com.example.weworkouttogether.utils.PreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var ft: FragmentTransaction
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var pref: PreferenceUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = PreferenceUtil(this.requireContext())
        ft = activity?.supportFragmentManager!!.beginTransaction()
        mFirebaseAuth = FirebaseAuth.getInstance()

        setFragmentResultListener("signUpKey") { _, bundle ->
            var result = bundle.getString("signUpData")
            Log.e("TAG", "$result")
            if (result != null) {
                binding.textView.text = result
            }
        }

        binding.btnLoginSignup.setOnClickListener {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            ft.replace(R.id.fragLogin, SignInFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.loginId.text.toString().trim()
            val pwd = binding.loginPwd.text.toString().trim()
            if(email.isNotEmpty() || pwd.isNotEmpty()) logIn(email, pwd) // null ??????
        }

    }

    private fun logIn(email: String, pwd: String) {
        mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = mFirebaseAuth.currentUser
                if (user != null) {
                    pref.setPref("autoLogin", "yes")
                    setUserData()
                    thread(start = true) {
                        Thread.sleep(1000L)
                        val myIntent = Intent(activity, MainActivity::class.java)
                        startActivity(myIntent)
                        activity?.finish()

                    }
                }
            } else {
                Toast.makeText(activity, "????????? ???????????? ???????????? ??????????????? ????????? ?????????", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val user = Firebase.auth.currentUser
        var mDatabase = FirebaseDatabase.getInstance().reference

        mDatabase.child("workout").child("UserAccount").child(user?.uid.toString()).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val admin = it.result?.child("admin")?.value.toString()
                    // ????????????????
                    if (admin == "true") {
                        Log.i("TAG", "Got value $admin")
                        pref.setPref("admin", admin)
                        // idToken ??????
                        pref.setPref("uid",it.result?.child("idToken")!!.value.toString())
                        // ???????????????
                        pref.setPref("email", it.result?.child("email")!!.value.toString())
                    } else {
                        //?????? ?????????
                        pref.setPref("uid",it.result?.child("idToken")!!.value.toString())
                        pref.setPref("email", it.result?.child("email")!!.value.toString())
                    }
                    Log.i("TAG", "Got value ${it.result}")
                }
            }.addOnFailureListener { Log.e("TAG", "Fail ${it.message}") }

    }



    private fun sinUpAnim() {
        val du: Long = 500
        val frag = activity?.fragSignup!!
        val anim = AnimationUtils.loadAnimation(context, R.anim.enter_from_right)
        anim.fillAfter = true
        frag.startAnimation(anim)
        frag.visibility = View.VISIBLE
//        val anim = TranslateAnimation(frag.width.toFloat(), 0f, 0f, 0f)
//        anim.duration = du
//        anim.fillAfter = true
//        frag.animation = anim
//        frag.visibility = View.VISIBLE
    }

}