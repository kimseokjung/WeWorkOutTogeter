package com.example.weworkouttogether.adater


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.weworkouttogether.databinding.ActivityMainBinding
import com.example.weworkouttogether.fragments.HomeFragment
import com.example.weworkouttogether.fragments.InfoFragment
import com.example.weworkouttogether.fragments.MapFragment
import com.example.weworkouttogether.fragments.ProfileFragment


class NavigationViewPagerAdater(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    private lateinit var binding: ActivityMainBinding

    var list = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
       return when(position){
          0 -> HomeFragment()
          1 -> MapFragment()
          2 -> InfoFragment()
          else -> ProfileFragment()
        }
    }
    override fun getCount(): Int {
        return 4
    }





}