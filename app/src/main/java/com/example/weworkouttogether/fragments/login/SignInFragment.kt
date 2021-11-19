package com.example.weworkouttogether.fragments.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import com.example.weworkouttogether.LogInActivity
import com.example.weworkouttogether.R
import com.example.weworkouttogether.databinding.FragmentSignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignInFragment : Fragment(), LogInActivity.OnBackPressedListener, View.OnClickListener {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var ft: FragmentTransaction
    private lateinit var list: MutableList<Bundle>
    private lateinit var mFirebaseAuth: FirebaseAuth // 파이어베이스 인증
    private lateinit var mDatabaseRef: DatabaseReference // 실시간 데이터 베이스


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
        binding.btnIdCheck.setOnClickListener(this)
        binding.editTextTextPassword.addTextChangedListener(TextWatcher(binding.editTextTextPassword))
        binding.editTextTextPassword2.addTextChangedListener(TextWatcher(binding.editTextTextPassword2))
    }

    private fun init() {
        mDatabaseRef = FirebaseDatabase.getInstance().reference

        ft = activity?.supportFragmentManager!!.beginTransaction()
        list = mutableListOf()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignUpBack -> { // 뒤로가기 눌렀을때
                Log.d("TAG", "btn back")
                goToBack()
            }
            R.id.btnSignInSubmit -> { //회원가입 눌렀을때
                Log.d("TAG", "btn submit")
                val email = binding.editSignInID.text.toString()
                val pwd = binding.tvPwdCheck.text.toString()
                // 로그인 프레그먼트로 데이터 전송
                // setFragmentResult("signUpKey", bundleOf("signUpData" to result))

                //firebase 인증 진행
                LogInActivity().signIn(email,pwd)

                goToBack()
            }
            R.id.btnIdCheck -> {
                var isUsable: Boolean = false
                if (isUsable) {
                    binding.tvIdCheck.text = "사용 할 수 있는 이메일입니다"
                    binding.tvIdCheck.setTextColor(Color.BLUE)
                } else {
                    binding.tvIdCheck.visibility = View.VISIBLE
                }
            }
            else -> {
            }
        }

    }

    inner class TextWatcher(private val v: View) : android.text.TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // text가 변경될때마다
        }

        override fun afterTextChanged(s: Editable?) {
            when (v.id) {
                R.id.editTextTextPassword -> {
                    if (textValidate(s.toString().trim())) {
                        binding.tvPwdCheck.text = "Good!"
                        binding.tvPwdCheck.setTextColor(Color.BLUE)
                    } else {
                        binding.tvPwdCheck.text = "비밀번호가 짧아요"
                        binding.tvPwdCheck.setTextColor(Color.RED)
                    }
                }
                R.id.editTextTextPassword2 -> {
                    val pwdCheck2 = binding.tvPwdPwdCheck
                    pwdCheck2.visibility = View.VISIBLE
                    if (Pattern.matches(binding.editTextTextPassword.text.toString(), s)) {
                        pwdCheck2.text = "Good!"
                        pwdCheck2.setTextColor(Color.BLUE)
                    } else {
                        pwdCheck2.text = "비밀번호가 일치하지 않아요"
                        pwdCheck2.setTextColor(Color.RED)
                    }
                }
                else -> {}
            }

        }
    }

    private fun textValidate(str: String): Boolean {
        val pwdPattern = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{6,18}$";
        val pattern: Pattern = Pattern.compile(pwdPattern);
        val matcher: Matcher = pattern.matcher(str);
        return matcher.matches();
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