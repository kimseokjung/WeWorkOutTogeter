package com.example.weworkouttogether.adater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weworkouttogether.R
import com.example.weworkouttogether.datas.PostUrl

class PostAdapter(private val mContext: Context) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var datas = mutableListOf<PostUrl>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.store_list_item, parent, false)
        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClickListener(v: View, data: PostUrl, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvBlogTitle : TextView = itemView.findViewById(R.id.tvBlogTile)
        private val imgBlogUrl : ImageView = itemView.findViewById(R.id.ivBlogImg)



//        private val roomImg: ImageView = itemView.findViewById(R.id.rvImg)

        fun bind(postUrl: PostUrl) {
            tvBlogTitle.text = postUrl.postTitle
            Glide.with(mContext).load(postUrl.postPhoto).into(imgBlogUrl)



            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onClickListener(itemView, postUrl, position)
                }
            }
        }

    }


}