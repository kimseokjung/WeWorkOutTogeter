package com.example.weworkouttogether.adater

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weworkouttogether.App
import com.example.weworkouttogether.data.PostSelectionItem
import com.example.weworkouttogether.data.PostSingleItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weworkouttogether.R
import com.example.weworkouttogether.ViewStoreDetailMainActivity
import com.gun0912.tedpermission.provider.TedPermissionProvider.context


class PostSelectionAdapter() : RecyclerView.Adapter<PostSelectionAdapter.ViewHolder>() {

    var datas = mutableListOf<PostSelectionItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(App.instance).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClickListener(v: View, data: PostSingleItem, position: Int)
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
        private val tvCategoryTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)
        private val btnMore: TextView = itemView.findViewById(R.id.btnMore)
        private val recycleSelection: RecyclerView = itemView.findViewById(R.id.rvSelectionList)

        fun bind(selectItem: PostSelectionItem) {
            var postSelectionItem = selectItem.singItemList
            tvCategoryTitle.text = selectItem.headerTitle
            recycleSelection.setHasFixedSize(true)
            recycleSelection.layoutManager = LinearLayoutManager(App.instance,LinearLayoutManager.HORIZONTAL,false)
            var postSingleAdapter = PostSingleAdapter(postSelectionItem)
            recycleSelection.adapter = postSingleAdapter
            btnMore.setOnClickListener {
                Toast.makeText(it.context, "More Click", Toast.LENGTH_SHORT).show()
            }


        }



    }


}
//    override fun onBindViewHolder(itemRowHolder: ItemRowHolder, i: Int) {
//        val sectionName: String = dataList!![i].getHeaderTitle()
//        val singleSectionItems: ArrayList<*> = dataList[i].getSingItemList()
//        itemRowHolder.itemTitle.text = sectionName
//        val itemListDataAdapter = SingleDataAdapter(mContext, singleSectionItems)
//        itemRowHolder.recycler_view_list.setHasFixedSize(true)
//        itemRowHolder.recycler_view_list.layoutManager =
//            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
//        itemRowHolder.recycler_view_list.adapter = itemListDataAdapter
//        itemRowHolder.btnMore.setOnClickListener { v ->
//            Toast.makeText(
//                v.context,
//                "click event on more, $sectionName",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }

