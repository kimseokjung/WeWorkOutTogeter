package com.example.weworkouttogether.adater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weworkouttogether.App
import com.example.weworkouttogether.R
import com.example.weworkouttogether.data.ListItemViewModel
import com.example.weworkouttogether.data.PostUrl
import com.example.weworkouttogether.data.ViewModelFactory
import com.example.weworkouttogether.databinding.StoreListItemBinding

class PostAdapter() :
    PagingDataAdapter<PostUrl, PostAdapter.PageViewHolder>(DIFF_COMPARATOR) {
    companion object {
        private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<PostUrl>() {
            override fun areItemsTheSame(oldItem: PostUrl, newItem: PostUrl): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(oldItem: PostUrl, newItem: PostUrl): Boolean {
                return oldItem == newItem
            }

        }

    }

    var datas = mutableListOf<PostUrl>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        var binding = StoreListItemBinding.inflate(LayoutInflater.from(App.instance), parent, false)
        var viewModel = ViewModelProvider(
            ViewModelStore(),
            ViewModelFactory(null)
        ).get(ListItemViewModel::class.java)
        return PageViewHolder(binding, viewModel)
    }

    interface OnItemClickListener {
        fun onClickListener(v: View, data: PostUrl, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindTo(it)
        }
    }


    inner class PageViewHolder(
        private val binding: StoreListItemBinding,
        var viewModel: ListItemViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvBlogTitle: TextView = itemView.findViewById(R.id.tvBlogTile)
        private val imgBlogUrl: ImageView = itemView.findViewById(R.id.ivBlogImg)

        fun bindTo(postUrl: PostUrl) {
            binding.model = viewModel
            this.viewModel.setIdText(postUrl.idx.toString())
            this.viewModel.setTitleText(postUrl.postTitle.toString())
            this.viewModel.setUrlText(postUrl.postUrl.toString())
            Glide.with(App.instance).load(postUrl.postPhoto).into(imgBlogUrl)

            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onClickListener(itemView, postUrl, position)
                }

            }


        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvBlogTitle: TextView = itemView.findViewById(R.id.tvBlogTile)
        private val imgBlogUrl: ImageView = itemView.findViewById(R.id.ivBlogImg)


//        private val roomImg: ImageView = itemView.findViewById(R.id.rvImg)

        fun bind(postUrl: PostUrl) {
            tvBlogTitle.text = postUrl.postTitle
            Glide.with(App.instance).load(postUrl.postPhoto).into(imgBlogUrl)


            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onClickListener(itemView, postUrl, position)
                }
            }
        }

    }


}