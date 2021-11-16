package com.example.weworkouttogether.fragments.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import com.example.weworkouttogether.R
import com.example.weworkouttogether.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.activity_log_in.*


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var ft: FragmentTransaction
    private lateinit var fm: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        result = arguments?.getString("signupData").toString()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ft = activity?.supportFragmentManager!!.beginTransaction()

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

        }

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