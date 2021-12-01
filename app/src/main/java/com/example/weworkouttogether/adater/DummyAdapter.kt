//package com.example.weworkouttogether.adater
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelStore
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.weworkouttogether.App
//import com.example.weworkouttogether.R
//import com.example.weworkouttogether.data.ListItemViewModel
//import com.example.weworkouttogether.data.PostSingleItem
//import com.example.weworkouttogether.data.ViewModelFactory
//import com.example.weworkouttogether.databinding.StoreListItemBinding
//
//class DummyAdapter() :
//    PagingDataAdapter<PostSingleItem, DummyAdapter.PageViewHolder>(DIFF_COMPARATOR) {
//    companion object {
//        private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<PostSingleItem>() {
//            override fun areItemsTheSame(oldItem: PostSingleItem, newItem: PostSingleItem): Boolean {
//                return oldItem.idx == newItem.idx
//            }
//
//            override fun areContentsTheSame(oldItem: PostSingleItem, newItem: PostSingleItem): Boolean {
//                return oldItem == newItem
//            }
//
//        }
//
//    }
//
//    var datas = mutableListOf<PostSingleItem>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
//        var binding = StoreListItemBinding.inflate(LayoutInflater.from(App.instance), parent, false)
//        var viewModel = ViewModelProvider(
//            ViewModelStore(),
//            ViewModelFactory(null)
//        ).get(ListItemViewModel::class.java)
//        return PageViewHolder(binding, viewModel)
//    }
//
//    interface OnItemClickListener {
//        fun onClickListener(v: View, data: PostSingleItem, position: Int)
//    }
//
//    private var listener: OnItemClickListener? = null
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }
//
//    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
//        getItem(position)?.let {
//            holder.bindTo(it)
//        }
//    }
//
//
//    inner class PageViewHolder(
//        private val binding: StoreListItemBinding,
//        var viewModel: ListItemViewModel
//    ) :
//        RecyclerView.ViewHolder(binding.root) {
//        private val tvBlogTitle: TextView = itemView.findViewById(R.id.tvPostTitle)
//        private val imgBlogUrl: ImageView = itemView.findViewById(R.id.ivBlogImg)
//
//        fun bindTo(postSingleItem: PostSingleItem) {
//
//            this.viewModel.setIdText(postSingleItem.idx.toString())
//            this.viewModel.setTitleText(postSingleItem.postTitle.toString())
//            this.viewModel.setUrlText(postSingleItem.postUrl.toString())
//            Glide.with(App.instance).load(postSingleItem.postPhoto).into(imgBlogUrl)
//
//            val position = layoutPosition
//            if (position != RecyclerView.NO_POSITION) {
//                itemView.setOnClickListener {
//                    listener?.onClickListener(itemView, postSingleItem, position)
//                }
//
//            }
//
//
//        }
//
//    }
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val tvBlogTitle: TextView = itemView.findViewById(R.id.tvPostTitle)
//        private val imgBlogUrl: ImageView = itemView.findViewById(R.id.ivBlogImg)
//
//
////        private val roomImg: ImageView = itemView.findViewById(R.id.rvImg)
//
//        fun bind(postSingleItem: PostSingleItem) {
//            tvBlogTitle.text = postSingleItem.postTitle
//            Glide.with(App.instance).load(postSingleItem.postPhoto).into(imgBlogUrl)
//
//
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                itemView.setOnClickListener {
//                    listener?.onClickListener(itemView, postSingleItem, position)
//                }
//            }
//        }
//
//    }
//
//
//}