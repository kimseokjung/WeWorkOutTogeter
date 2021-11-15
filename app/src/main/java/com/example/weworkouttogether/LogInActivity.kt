package com.example.weworkouttogether


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.slidingpanelayout.widget.SlidingPaneLayout

import com.example.weworkouttogether.databinding.ActivityLogInBinding
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var ft: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        //binding 재정의
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //baseActivity 상속함수 실행
        setUpEvents()
        setValues()

        binding.btnLoginSignup.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)

        when (v?.id) {
            R.id.btnLoginSignup -> {
                Log.d("TAG", "setUpEvents: 회원가입 눌림??")
                fragSignup.visibility = View.VISIBLE
                ft.replace(R.id.fragSignup,SignInFragment()).addToBackStack(null).commit()
//                SignInFragment().show(supportFragmentManager,"TAG_FRAG_SIGNUP")
            }
            R.id.btnLogin -> {
                Log.d("TAG", "setUpEvents: 로그인 눌림??")
            }
            else -> {
                Toast.makeText(mContext, "딴거", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setUpEvents() {}


    override fun setValues() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
//        finish()
    }


}