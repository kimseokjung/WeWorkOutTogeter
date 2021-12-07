package com.example.weworkouttogether.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weworkouttogether.App
import com.example.weworkouttogether.R
import com.example.weworkouttogether.adater.PostSelectionAdapter
import com.example.weworkouttogether.data.*
import com.example.weworkouttogether.databinding.FragmentHomeBinding
import com.example.weworkouttogether.utils.PreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class HomeFragment : Fragment(), View.OnClickListener, LifecycleOwner {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mViewLifecycleOwner: LifecycleOwner
    private lateinit var postSelectionAdapter: PostSelectionAdapter
    private val postDatas = mutableListOf<PostSelectionItem>()
    private lateinit var viewModel: PageViewModel
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var pref: PreferenceUtil
    private lateinit var db: UrlDatabase

    private val scope = CoroutineScope(Dispatchers.Default)

    // thesem blog page url
    var pilatesUri =
        "https://blog.naver.com/PostList.naver?from=postList&blogId=thesemkorea&parentCategoryNo=9&currentPage="
    var yogaUri =
        "https://blog.naver.com/PostList.naver?from=postList&blogId=thesemkorea&categoryNo=10&parentCategoryNo=10&currentPage="

    var pages = 1
    var maxPages = 2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.mViewLifecycleOwner = this
        this.binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel =
            ViewModelProvider(this, ViewModelFactory(App.instance))
                .get(PageViewModel::class.java)
        this.postSelectionAdapter = PostSelectionAdapter()
        binding.homePeedList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.homePeedList.setHasFixedSize(true)
        binding.homePeedList.adapter = postSelectionAdapter

        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        pref = PreferenceUtil(App.instance)
        db = UrlDatabase.getInstance() as UrlDatabase

        //init coroutine
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressBarLayout.visibility = View.VISIBLE
            withContext(scope.coroutineContext) {
                // data를 받아온다
                initRecycler()
            }
            delay(1000)
            postSelectionAdapter.notifyDataSetChanged()
            binding.progressBarLayout.visibility = View.GONE
        }

        binding.homeFloating.setOnClickListener(this)
        binding.homePeedList.setOnClickListener(this)


        // Post item 클릭 리스너
//        postSingleAdapter.setOnItemClickListener(object : PostSingleAdapter.OnItemClickListener {
//            override fun onClickListener(v: View, data: PostSingleItem, position: Int) {
//                val storeIntent =
//                    Intent(context, VIewStoreDetailMainActivity::class.java).apply {
//                        putExtra("datas", data.postUrl)
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    }.run { startActivity(this) }
//            }

//        })

        val isAdmin = pref.getData("admin")
        Log.e("TAG", "initRecycler: $isAdmin")
        if (isAdmin == "true") {
            binding.homeFloating.visibility = View.VISIBLE
        }

    }

    private fun createPilatesData(): MutableList<PostSingleItem> {
        var singleList = mutableListOf<PostSingleItem>()
        try {
            var doc = Jsoup.connect(pilatesUri).get()
            var pilates = doc.select("div.post_album_view_s966 div ul li a")

            for (i in 0..5) {
                var e = pilates[i]
                var url = e.absUrl("href")
                var title = e.select("div.area_text strong").text()
                var src = e.select("div.area_thumb img").first()
                var photoUrl = src.absUrl("src")

                Log.d("TAG", url)
                Log.d("TAG", title)
                Log.d("TAG", photoUrl)
                try {
                    singleList.add(PostSingleItem(null, url, title, photoUrl))

                } catch (e: Exception) {
                    Log.e("TAG", "Pilates data : ${e.toString()}")
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "Pilates last : ${e.toString()}")
        }
        return singleList
    }


    private fun initRecycler() {
        try {
            for (i in 0..5) {
                Log.e("TAG", "initRecycler: ${createPilatesData()}")
                postDatas.add(PostSelectionItem("Selection $i", createPilatesData()))
            }
        } catch (e: Exception) {
            Log.e("TAG", "init last : ${e.toString()}")
        }
        Log.d("TAG", "크롤링 끝")
        postSelectionAdapter.datas = postDatas

    }



    inner class UrlRum() : Runnable {
        lateinit var elements: Elements

        @Synchronized
        override fun run() {
            try {
                var doc = Jsoup.connect(pilatesUri).get()
                elements = doc.select("div.post_album_view_s966 div ul li a")

                var db = Firebase.database.getReference("workout")

                for (e in elements) {
                    var url = e.absUrl("href")
                    var title = e.select("div.area_text strong").text()
                    var src = e.select("div.area_thumb img").first()
                    var photoUrl = src.absUrl("src")

                    Log.d("TAG", url)
                    Log.d("TAG", title)
                    Log.d("TAG", photoUrl)
                    var insertData = hashMapOf(
                        "url" to url,
                        "title" to title,
                        "src" to src,
                        "photoUrl" to photoUrl
                    )
                    try {
                        db.child("post").child("yoga").child(title)
                            .setValue(insertData)
                            .addOnSuccessListener { Log.d("firebase", "Yoga Data add Success!!") }
                            .addOnFailureListener { Log.d("firebase", "Yoga Data add Fail!!") }

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
        Log.d("TAG", "onClick: $v")
        when (v?.id) {
            R.id.homeFloating -> {
                Toast.makeText(this.context, "More add", Toast.LENGTH_SHORT).show()
            }
            R.id.homePeedList -> {

            }
            else -> {}
        }
    }

}