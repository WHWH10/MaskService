package com.ejcho.maskservice

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ejcho.maskservice.model.Store

class StoreAdapter: RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    private var storeList: List<Store> = ArrayList<Store>()
    class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val addressTextView: TextView = itemView.findViewById(R.id.addr_text_view)
        val remainTextView: TextView = itemView.findViewById(R.id.remain_text_view)
        val countTextView: TextView = itemView.findViewById(R.id.count_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store: Store = storeList[position]

        holder.nameTextView.text = store.name
        holder.addressTextView.text = store.addr

        var remainStat = "충분"
        var count = "100개 이상"
        var color = Color.GREEN

        when (store.remain_stat) {
            "plenty" -> {
                remainStat = "충분"
                count = "100개 이상"
                color = Color.GREEN
            }
            "some" -> {
                remainStat = "여유"
                count = "30개 이상"
                color = Color.YELLOW
            }
            "few" -> {
                remainStat = "매진임박"
                count = "2개 이상"
                color = Color.RED
            }
            "empty" -> {
                remainStat = "재고없음"
                count = "1개 이하"
                color = Color.GRAY
            }
            else -> {

            }
        }

        holder.remainTextView.text = remainStat
        holder.countTextView.text = count
        holder.remainTextView.setTextColor(color)
        holder.countTextView.setTextColor(color)
    }

    fun updateItem(newStoreList: List<Store>) {
        storeList = newStoreList
        notifyDataSetChanged() // UI 갱신
    }

}