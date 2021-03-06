package com.example.weworkouttogether.adater


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weworkouttogether.App
import com.example.weworkouttogether.Interface.MyItemClickListener
import com.example.weworkouttogether.R
import com.example.weworkouttogether.ShowPostDetailActivity
import com.example.weworkouttogether.data.PostSingleItem

class PostSingleAdapter(postSelectionItem: MutableList<PostSingleItem>) :
    RecyclerView.Adapter<PostSingleAdapter.ViewHolder>() {

    var datas: MutableList<PostSingleItem> = postSelectionItem
    var context: Context = App.instance.applicationContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(App.instance).inflate(R.layout.store_list_item, parent, false)
        return ViewHolder(view)
    }
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.setOnClick(object :MyItemClickListener{
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(App.instance, "${datas[position].postTitle} 이잉잉", Toast.LENGTH_SHORT).show()
                var intent = Intent(App.instance, ShowPostDetailActivity::class.java)
                intent.putExtra("test","${datas[position].postTitle}")
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                //(holder.itemView.context as Activity).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left) 
                // 애니메이션 실패

            }

        })
    }

    override fun getItemCount(): Int = datas.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        private val tvPostTitle: TextView = itemView.findViewById(R.id.tvPostTitle)
        private val imgPostUrl: ImageView = itemView.findViewById(R.id.ivPostImg)

        lateinit var myItemClickListener: MyItemClickListener
        fun setOnClick(myItemClickListener: MyItemClickListener) {
            this.myItemClickListener = myItemClickListener
        }

        init {
            view.setOnClickListener(this)
        }

        fun bind(postSingleItem: PostSingleItem) {
            tvPostTitle.text = postSingleItem.postTitle
            Glide.with(App.instance).load(postSingleItem.postPhoto).into(imgPostUrl)


//            val position = bindingAdapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                itemView.setOnClickListener {
//                    listener?.onClickListener(itemView, postSingleItem, position)
//                }
//            }

        }

        override fun onClick(v: View?) {
            myItemClickListener.onItemClickListener(v!!,bindingAdapterPosition)
        }

    }

}


