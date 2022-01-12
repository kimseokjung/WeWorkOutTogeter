package com.example.weworkouttogether.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.example.weworkouttogether.R
import com.example.weworkouttogether.databinding.FragmentProfileBinding
import com.example.weworkouttogether.fragments.profiletab.Tab1Fragment
import com.example.weworkouttogether.fragments.profiletab.Tab2Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var tabs: TabLayout
    private lateinit var tab1F: Tab1Fragment
    private lateinit var tab2F: Tab2Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab1F = Tab1Fragment()
        tab2F = Tab2Fragment()
        parentFragmentManager.beginTransaction().replace(R.id.profile_frame, tab1F).commit();

        tabs = binding.profileTabs
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            lateinit var selected:Fragment
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {selected = tab1F}
                    1 -> {selected = tab2F}
                }
                parentFragmentManager.beginTransaction().replace(R.id.profile_frame, selected).commit();
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}