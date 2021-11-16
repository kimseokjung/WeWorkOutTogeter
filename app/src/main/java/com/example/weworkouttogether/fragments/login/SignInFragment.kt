package com.example.weworkouttogether.fragments.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import com.example.weworkouttogether.LogInActivity
import com.example.weworkouttogether.R
import com.example.weworkouttogether.databinding.FragmentSignInBinding
import kotlinx.android.synthetic.main.activity_log_in.*
import com.gun0912.tedpermission.provider.TedPermissionProvider.context

import com.example.weworkouttogether.MainActivity
import com.gun0912.tedpermission.provider.TedPermissionProvider


class SignInFragment : Fragment(), LogInActivity.OnBackPressedListener, View.OnClickListener {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var ft: FragmentTransaction
    private lateinit var list: MutableList<Bundle>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.btnSignUpBack.setOnClickListener(this)
        binding.btnSignInSubmit.setOnClickListener(this)
    }

    private fun init() {
        ft = activity?.supportFragmentManager!!.beginTransaction()
        list = mutableListOf()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignUpBack -> {
                Log.d("TAG", "btn back")
                goToBack()
            }
            R.id.btnSignInSubmit -> {
                Log.d("TAG", "btn submit")
                val result = binding.editSignInID.text.toString()

                setFragmentResult("signUpKey", bundleOf("signUpData" to result))
                goToBack()
            }
            else -> {
            }
        }

    }


    private fun goToBack() {
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
        ft.replace(R.id.fragLogin, LoginFragment()).commit()
    }

    // 리스너를 설정하기 위해 Activity 를 받아옵니다.
    private val log: LogInActivity = LogInActivity()
    override fun onBack() {
        Log.e("TAG", "onBack()")
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제해줍니다.
        log.setOnBackPressedListener(null)
        goToBack()
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면 하단 문장처럼 호출해주면 됩니다.
        // activity.onBackPressed();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드입니다.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "onAttach()")
        // 혹시 Context 로 안된다면 Activity
        (context as LogInActivity).setOnBackPressedListener(this)
    }


}