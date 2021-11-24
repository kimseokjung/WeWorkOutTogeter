package com.example.weworkouttogether.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weworkouttogether.MainActivity
import com.example.weworkouttogether.R
import com.example.weworkouttogether.VIewStoreDetailMainActivity
import com.example.weworkouttogether.adater.PostAdapter
import com.example.weworkouttogether.databinding.FragmentHomeBinding
import com.example.weworkouttogether.datas.PostUrl
import com.example.weworkouttogether.datas.UrlDatabase
import com.example.weworkouttogether.utils.PreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var storeAdapter: PostAdapter
    private val storeDatas = mutableListOf<PostUrl>()
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var pref: PreferenceUtil
    private lateinit var db: UrlDatabase
    private lateinit var postData: MutableList<PostUrl>

    // thesem blog page url
    var baseUri =
        "https://blog.naver.com/PostList.naver?from=postList&blogId=thesemkorea&parentCategoryNo=9&currentPage="
    var pages = 1
    var maxPages = 2


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
        loadData()

        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        pref = PreferenceUtil(requireContext().applicationContext)
        db = UrlDatabase.getInstance(requireActivity().applicationContext) as UrlDatabase
        postData = db.postUrlDao().getAll()
        initRecycler()


        binding.homeFloating.setOnClickListener(this@HomeFragment)

        val isAdmin = pref.getData("admin")
        Log.e("TAG", "initRecycler: $isAdmin")
        if (isAdmin == "true") {
            binding.homeFloating.visibility = View.VISIBLE
        }

        binding.homePeedList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.homePeedList.canScrollVertically(1)) {
                    Log.d("TAG", "onScrolled: 어디냐")
                    binding.progressBar.visibility = View.VISIBLE
                    pages + 2
                    maxPages + 2
                    loadData()
                    initRecycler()

                }
            }
        })

    }

    @Synchronized
    fun loadData() {
        synchronized(this) {
            while (pages <= maxPages) {
                var t = Thread(UrlRum(baseUri, pages, requireContext()))
                // thread실행
                t.start()
                // thread가 끝날때 까지 main thread 대기
                t.join()
                // 다음 page 크롤링
                pages++
            }
        }
        binding.progressBar.visibility = View.GONE
        Log.d("TAG", "크롤링 끝")
    }


    private fun initRecycler() {
        storeAdapter = PostAdapter(requireActivity())
        homePeedList.layoutManager = LinearLayoutManager(context)
        homePeedList.adapter = storeAdapter
        storeDatas.apply {
            for (e in postData) {
                add(
                    PostUrl(
                        null,
                        e.postUrl,
                        e.postTitle,
                        e.postPhoto
                    )
                )
            }
            storeAdapter.datas = storeDatas

            storeAdapter.setOnItemClickListener(object : PostAdapter.OnItemClickListener {
                override fun onClickListener(v: View, data: PostUrl, position: Int) {
                    val storeIntent =
                        Intent(context, VIewStoreDetailMainActivity::class.java).apply {
                            putExtra("datas", data.postUrl)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }.run { startActivity(this) }
                }

            })
            storeAdapter.notifyDataSetChanged()
        }
    }

    class UrlRum(var baseUrl: String, var pages: Int, var context: Context) : Runnable {
        lateinit var elements: Elements

        @Synchronized
        override fun run() {
            try {
                var doc = Jsoup.connect(baseUrl + pages).get()
                elements = doc.select("div.post_album_view_s966 div ul li a")
//                postTitle = doc.select("div.post_album_view_s966 div ul li a div.area_text strong")
//                postImg = doc.select("div.post_album_view_s966 div ul li a div.area_thumb img")

                var db = UrlDatabase.getInstance(context)

                for (e in elements) {
                    var url = e.absUrl("href")
                    var title = e.select("div.area_text strong").text()
                    var src = e.select("div.area_thumb img").first()
                    var photoUrl = src.absUrl("src")

                    Log.d("TAG", url)
                    Log.d("TAG", title)
                    Log.d("TAG", photoUrl)
                    try {
                        db?.postUrlDao()!!.insertUrl(PostUrl(null, url, title, photoUrl))
                    } catch (e: Exception) {
                        Log.e("TAG", e.toString())
                    }
                }

            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
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