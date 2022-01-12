package com.example.weworkouttogether


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.weworkouttogether.databinding.ActivityLogInBinding
import android.os.Process
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.weworkouttogether.fragments.login.LoginFragment
import com.example.weworkouttogether.utils.PreferenceUtil
import com.example.weworkouttogether.utils.RoomDataUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class LogInActivity : BaseActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var ft: FragmentTransaction
    private lateinit var pref: PreferenceUtil
    private lateinit var db: RoomDataUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = PreferenceUtil(applicationContext)
        db = RoomDataUtil()
        //시작할때 db 초기화 "posts"
        synchronized(this){
            db.clearAll()
            Log.d("TAG", "onCreate: 시작 데이터 삭제")
        }
        // auto login check
        if(pref.getData("autoLogin") == "yes"){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        //binding 재정의
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragLogin, LoginFragment()).commit()

        //baseActivity 상속함수 실행
        setUpEvents()
        setValues()


    }

    override fun setUpEvents() {

    }

    override fun setValues() {

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
    override fun onDestroy() {
        super.onDestroy()
    }

}