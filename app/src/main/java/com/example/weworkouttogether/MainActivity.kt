package com.example.weworkouttogether

import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView


import android.os.Bundle
import com.example.weworkouttogether.adater.NavigationViewPagerAdater
import com.example.weworkouttogether.databinding.ActivityMainBinding
import com.example.weworkouttogether.fragments.HomeFragment
import com.example.weworkouttogether.fragments.InfoFragment
import com.example.weworkouttogether.fragments.MapFragment
import com.example.weworkouttogether.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var nvpa: NavigationViewPagerAdater
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


    }

    override fun setUpEvents() {
        binding.mainTopBarSignOut.setOnClickListener{
            logOut()
        }
    }


    override fun setValues() {
        nvpa = NavigationViewPagerAdater(supportFragmentManager)
//        frg_container.adapter = nvpa

    }
    fun logOut(){
        mFirebaseAuth.signOut()
        val myIntent = Intent(this, LogInActivity::class.java)
        startActivity(myIntent)
        finish()
    }

}