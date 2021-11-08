package com.example.weworkouttogeter.adater


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.weworkouttogeter.fragments.HomeFragment
import com.example.weworkouttogeter.fragments.InfoFragment
import com.example.weworkouttogeter.fragments.MapFragment
import com.example.weworkouttogeter.fragments.ProfileFragment


class NavigationViewPagerAdater(fm : FragmentManager) : FragmentPagerAdapter(fm) {
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
        return list.size
    }


}