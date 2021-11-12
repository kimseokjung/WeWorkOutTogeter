package com.example.weworkouttogeter.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weworkouttogeter.MainActivity
import com.example.weworkouttogeter.R
import com.example.weworkouttogeter.adater.StoreAdapter
import com.example.weworkouttogeter.datas.Store
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var storeAdapter: StoreAdapter
    val storeDatas = mutableListOf<Store>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        storeAdapter = StoreAdapter(requireActivity())
        homePeedList.layoutManager = LinearLayoutManager(context)
        homePeedList.adapter = storeAdapter

        storeDatas.apply {

            add(
                Store(
                    "드러누워 필라테스",
                    "010-1111-2222",
                    R.drawable.parson,

                    )
            )


            storeAdapter.datas = storeDatas
        }
        storeAdapter.notifyDataSetChanged()
    }
}