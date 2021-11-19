package com.example.weworkouttogether


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.weworkouttogether.databinding.ActivityLogInBinding
import android.os.Process
import androidx.fragment.app.FragmentTransaction
import com.example.weworkouttogether.fragments.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LogInActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var ft: FragmentTransaction
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        //binding 재정의
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //baseActivity 상속함수 실행
        setUpEvents()
        setValues()

    }

    override fun onClick(v: View?) {

    }

    override fun setUpEvents() {

    }

    override fun setValues() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().reference
        ft = supportFragmentManager.beginTransaction()
        val loginFrag: LoginFragment = LoginFragment()
        ft.add(R.id.fragLogin, LoginFragment()).commit()
    }


    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private var pressedTime: Long = 0

    // 리스너 생성
    interface OnBackPressedListener {
        fun onBack()
    }

    // 리스너 객체 생성
    private var mBackListener: OnBackPressedListener? = null

    // 리스너 설정 메소드
    fun setOnBackPressedListener(listener: OnBackPressedListener?) {
        mBackListener = listener
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    override fun onBackPressed() {

        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if (mBackListener != null) {
            mBackListener!!.onBack()
            Log.e("!!!", "Listener is not null")
            // 리스너가 설정되지 않은 상태(예를들어 메인Fragment)라면
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료됩니다.
        } else {
            Log.e("!!!", "Listener is null")
            if (pressedTime == 0L) {
                Toast.makeText(mContext, "뒤로가기를 한번더 누르면 종료됨니다", Toast.LENGTH_SHORT).show()
                pressedTime = System.currentTimeMillis()
            } else {
                val seconds = (System.currentTimeMillis() - pressedTime).toInt()
                if (seconds > 2000) {

                    pressedTime = 0
                } else {
                    super.onBackPressed()
                    Log.e("!!!", "onBackPressed : finish, killProcess")
                    finish()
                    Process.killProcess(Process.myPid())
                }
            }
        }
    }

    fun signIn(email: String, pwd: String) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Log.e("TAG", "create User!!")
                val user = mFirebaseAuth.currentUser
                val userAccount = UserAccount(
                    user?.email.toString(),
                    pwd,
                    user?.uid.toString()
                )
                mDatabaseRef.child("UserAccount").child(user?.uid.toString())
                    .setValue(userAccount)

                Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(this, "회원가입 실패!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun logIn(email: String, pwd: String){
        mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener {
            if(it.isSuccessful){
              val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
                finish()
            }else{
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }


}