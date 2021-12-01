//package com.example.weworkouttogether.fragments
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.lifecycleScope
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.weworkouttogether.App
//import com.example.weworkouttogether.R
//import com.example.weworkouttogether.VIewStoreDetailMainActivity
//import com.example.weworkouttogether.adater.PilatesAdapter
//import com.example.weworkouttogether.databinding.FragmentHomeBinding
//import com.example.weworkouttogether.data.PageViewModel
//import com.example.weworkouttogether.data.PostUrl
//import com.example.weworkouttogether.data.UrlDatabase
//import com.example.weworkouttogether.data.ViewModelFactory
//import com.example.weworkouttogether.utils.PreferenceUtil
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import kotlinx.android.synthetic.main.fragment_home.*
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//import org.jsoup.Jsoup
//import org.jsoup.select.Elements
//
//class Dummy : Fragment(), View.OnClickListener, LifecycleOwner {
//    private lateinit var binding: FragmentHomeBinding
//    private lateinit var mViewLifecycleOwner: LifecycleOwner
//    private lateinit var postAdapter: PilatesAdapter
//    private val postDatas = mutableListOf<PostUrl>()
//    private lateinit var viewModel: PageViewModel
//    private lateinit var mFirebaseAuth: FirebaseAuth
//    private lateinit var mDatabase: DatabaseReference
//    private lateinit var pref: PreferenceUtil
//    private lateinit var db: UrlDatabase
//
//    private val scope = CoroutineScope(Dispatchers.Default)
//
//    // thesem blog page url
//    var baseUri =
//        "https://blog.naver.com/PostList.naver?from=postList&blogId=thesemkorea&parentCategoryNo=9&currentPage="
//    var pages = 1
//    var maxPages = 2
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        this.mViewLifecycleOwner = this
//        this.binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        this.viewModel =
//            ViewModelProvider(this, ViewModelFactory(App.instance))
//                .get(PageViewModel::class.java)
//        this.postAdapter = PilatesAdapter()
//        this.binding.homePeedList.layoutManager = GridLayoutManager(context,2)
//        this.binding.homePeedList.adapter = this.postAdapter
//
//        mFirebaseAuth = FirebaseAuth.getInstance()
//        mDatabase = FirebaseDatabase.getInstance().reference
//        pref = PreferenceUtil(requireContext().applicationContext)
//        db = UrlDatabase.getInstance() as UrlDatabase
//        scope.launch {
//            initRecycler()
//            loadData()
//            showData()
//        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            postAdapter.loadStateFlow.collectLatest { loadState ->
//                binding.progressBarLayout.isVisible = loadState.refresh is LoadState.Loading
//            }
//
//        }
//        binding.homeRefresh.setOnRefreshListener {
//            postAdapter.refresh()
//            binding.homeRefresh.isRefreshing = false
//        }
//
//
//        binding.homeFloating.setOnClickListener(this@Dummy)
//
//        // Post item 클릭 리스너
//        postAdapter.setOnItemClickListener(object : PilatesAdapter.OnItemClickListener {
//            override fun onClickListener(v: View, data: PostUrl, position: Int) {
//                val storeIntent =
//                    Intent(context, VIewStoreDetailMainActivity::class.java).apply {
//                        putExtra("datas", data.postUrl)
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    }.run { startActivity(this) }
//            }
//
//        })
//
//        val isAdmin = pref.getData("admin")
//        Log.e("TAG", "initRecycler: $isAdmin")
//        if (isAdmin == "true") {
//            binding.homeFloating.visibility = View.VISIBLE
//        }
//
//    }
//
//
//    private suspend fun showData() {
//        viewModel.data.collectLatest {
//            postAdapter.submitData(it)
//        }
//    }
//
//    @Synchronized
//    fun loadData() {
//        synchronized(this) {
//
//            var t = Thread(UrlRum())
//            // thread실행
//            t.start()
//            // thread가 끝날때 까지 main thread 대기
//            t.join()
//            // 다음 page 크롤링
//
//        }
//
//        Log.d("TAG", "크롤링 끝")
//    }
//
//
//    private fun initRecycler() {
//        postDatas.apply {
//            for (e in this) {
//                add(
//                    PostUrl(
//                        null,
//                        e.postUrl,
//                        e.postTitle,
//                        e.postPhoto
//                    )
//                )
//            }
//            postAdapter.datas = postDatas
//            postAdapter.notifyDataSetChanged()
//        }
//    }
//    fun isEndPage(){
//
//    }
//
//    inner class UrlRum() : Runnable {
//        lateinit var elements: Elements
//
//        @Synchronized
//        override fun run() {
//            try {
//                var doc = Jsoup.connect(baseUri).get()
//                elements = doc.select("div.post_album_view_s966 div ul li a")
//
//                var db = UrlDatabase.getInstance()
//
//                for (e in elements) {
//                    var url = e.absUrl("href")
//                    var title = e.select("div.area_text strong").text()
//                    var src = e.select("div.area_thumb img").first()
//                    var photoUrl = src.absUrl("src")
//
//                    Log.d("TAG", url)
//                    Log.d("TAG", title)
//                    Log.d("TAG", photoUrl)
//                    try {
//                        db?.postUrlDao()!!.insertUrl(PostUrl(null, url, title, photoUrl))
//
//                    } catch (e: Exception) {
//                        Log.e("TAG", e.toString())
//                    }
//                }
//
//            } catch (e: Exception) {
//                Log.e("TAG", e.toString())
//            }
//        }
//
//    }
//
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.homeFloating -> {
//
//            }
//            else -> {}
//        }
//    }
//
//}