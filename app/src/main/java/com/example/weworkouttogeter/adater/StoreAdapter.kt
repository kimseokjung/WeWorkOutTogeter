package com.example.weworkouttogeter.adater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weworkouttogeter.R
import com.example.weworkouttogeter.datas.Store
import kotlinx.android.synthetic.main.store_list_item.view.*

class StoreAdapter(private val mContext: Context) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    var datas = mutableListOf<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.store_list_item, parent, false)
        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClickListener(v: View, data: Store, position: Int)
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
        private val view : View = view
        private val tvStoreTitle : TextView = itemView.findViewById(R.id.tvStoreTile)
        private val tvStoreTel : TextView = itemView.findViewById(R.id.tvStoreTel)
        private val circleImageView : ImageView = itemView.findViewById(R.id.circleImageView)

//        private val roomImg: ImageView = itemView.findViewById(R.id.rvImg)

        fun bind(store: Store) {
            tvStoreTitle.text = store.name
            tvStoreTel.text = store.tel
            Glide.with(mContext).load(store.photoUrl).circleCrop().into(circleImageView)
//           tvRoomPrice.text = "${room.getFormatPrice()}/${room.minPrice}"
//           tvRoomType.text = "${room.type}｜방${room.room_num}개｜${room.getFormatFloor()}｜관리비 ${room.sub_price}만원"


            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onClickListener(itemView, store, position)
                }
            }
        }

    }

}