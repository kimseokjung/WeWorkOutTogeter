package com.example.weworkouttogether.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weworkouttogether.R
import com.example.weworkouttogether.VIewStoreDetailMainActivity
import com.example.weworkouttogether.adater.StoreAdapter
import com.example.weworkouttogether.datas.Store
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
            add(
                Store(
                    "나도몰라 필라테스",
                    "010-3333-2222",
                    R.drawable.parson,

                    )
            )
            add(
                Store(
                    "가즈아 필라테스",
                    "010-4422-2322",
                    R.drawable.parson,

                    )
            )
            add(
                Store(
                    "아파디져 필라테스",
                    "010-1331-2598",
                    R.drawable.parson,

                    )
            )


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
}