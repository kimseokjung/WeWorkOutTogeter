package com.example.weworkouttogether.fragments


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weworkouttogether.MainActivity
import com.example.weworkouttogether.R
import com.example.weworkouttogether.Storage
import com.example.weworkouttogether.VIewStoreDetailMainActivity
import com.example.weworkouttogether.adater.StoreAdapter
import com.example.weworkouttogether.databinding.FragmentHomeBinding
import com.example.weworkouttogether.datas.Store
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var storeAdapter: StoreAdapter
    private val storeDatas = mutableListOf<Store>()
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        sharedPref = activity?.getSharedPreferences("admin", MODE_PRIVATE)!!

        initRecycler()
        binding.homeFloating.setOnClickListener(this@HomeFragment)
        val isAdmin = sharedPref.getBoolean("admin",false)
        Log.e("TAG", "initRecycler: $isAdmin")
        if (isAdmin) {
            binding.homeFloating.visibility = View.VISIBLE
        }

    }


    private fun initRecycler() {
        storeAdapter = StoreAdapter(requireActivity())
        homePeedList.layoutManager = LinearLayoutManager(context)
        homePeedList.adapter = storeAdapter

        storeDatas.apply {

//            add(
//                Store(
//                    "드러누워 필라테스",
//                    "010-1111-2222",
//                    R.drawable.parson,
//
//                    )
//            )


            storeAdapter.datas = storeDatas

            storeAdapter.setOnItemClickListener(object : StoreAdapter.OnItemClickListener {
                override fun onClickListener(v: View, data: Store, position: Int) {
                    val storeIntent =
                        Intent(context, VIewStoreDetailMainActivity::class.java).apply {
                            putExtra("datas", data)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }.run { startActivity(this) }
                }

            })
            storeAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.homeFloating -> {

            }
            else -> {}
        }
    }
}