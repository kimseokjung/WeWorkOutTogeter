package com.example.weworkouttogeter

import com.google.android.material.bottomnavigation.BottomNavigationView


import android.os.Bundle
import com.example.weworkouttogeter.adater.NavigationViewPagerAdater
import com.example.weworkouttogeter.databinding.ActivityMainBinding
import com.example.weworkouttogeter.fragments.HomeFragment
import com.example.weworkouttogeter.fragments.InfoFragment
import com.example.weworkouttogeter.fragments.MapFragment
import com.example.weworkouttogeter.fragments.ProfileFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var nvpa : NavigationViewPagerAdater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

    override fun setupEvents() {
        bnv_main.setOnItemSelectedListener { id ->
            when (id) {
                R.id.navHome -> vp.currentItem = 0
                R.id.navProfile -> vp.currentItem = 1
                R.id.navInfo -> vp.currentItem = 2
                R.id.navProfile -> vp.currentItem = 3
            }
        }
    }

    override fun setValues() {
        nvpa = NavigationViewPagerAdater(supportFragmentManager)
        vp.adapter = nvpa





    }

}