package com.example.weworkouttogether


import android.os.Bundle
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.example.weworkouttogether.adater.NavigationViewPagerAdater
import com.example.weworkouttogether.databinding.ActivityMainBinding
import com.example.weworkouttogether.fragments.HomeFragment
import com.example.weworkouttogether.fragments.InfoFragment
import com.example.weworkouttogether.fragments.MapFragment
import com.example.weworkouttogether.fragments.ProfileFragment
import com.example.weworkouttogether.utils.RoomDataUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.*
import com.example.weworkouttogether.utils.ForcedTerminationService

import android.content.Intent




class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var nvpa: NavigationViewPagerAdater
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var db: RoomDataUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, ForcedTerminationService::class.java))
        setContentView(R.layout.activity_main)
        db = RoomDataUtil()
//        setUserData()


        setUpEvents()
        setValues()

        mFirebaseAuth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var bnvMain = findViewById<BottomNavigationView>(R.id.bnv_main)

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        bnvMain.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navHome -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frg_container, homeFragment).commit()
                    }
                    R.id.navMap -> {
                        val mapFragment = MapFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frg_container, mapFragment).commit()
                    }
                    R.id.navInfo -> {
                        val infoFragment = InfoFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frg_container, infoFragment).commit()
                    }
                    else -> {
                        val profileFragment = ProfileFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frg_container, profileFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.navHome
        }


        binding.mainTopBarSignOut.setOnClickListener {
            val dlg = LogOutDialog(this@MainActivity)
            dlg.setOnOKClickListener { content ->
            }
            dlg.start("로그아웃 ㄱㄱ?")

        }


    }




    override fun setUpEvents() {

    }


    override fun setValues() {
        nvpa = NavigationViewPagerAdater(supportFragmentManager)
//        frg_container.adapter = nvpa

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
        db.clearAll()
        Log.d("TAG", "onDestroy: data clear!!")
    }

}