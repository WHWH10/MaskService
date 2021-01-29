package com.ejcho.maskservice

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ejcho.maskservice.databinding.ItemStoreBinding
import com.ejcho.maskservice.model.Store

class StoreAdapter(val context: Context) :
    RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    private var storeList: List<Store> = ArrayList<Store>()

    inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 바인딩 객체
        val binding = ItemStoreBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {

        holder.binding.store = storeList[position]

    }

    fun updateItem(newStoreList: List<Store>) {
        storeList = newStoreList
        notifyDataSetChanged() // UI 갱신
    }
}

// 데이터바인딩
@BindingAdapter("remainStat")
fun setRemainStat(
    remainTextView: TextView,
    store: Store
) = when (store.remain_stat) {
    "plenty" -> {
        remainTextView.text = "충분"
    }

    "some" -> {
        remainTextView.text = "여유"
    }

    "few" -> {
        remainTextView.text = "매진임박"
    }

    else -> {
        remainTextView.text = "재고없음"
    }

}

@BindingAdapter("count")
fun setCount(
    countTextView: TextView,
    store: Store
) = when (store.remain_stat) {
    "plenty" -> {
        countTextView.text = "100개 이상"
    }

    "some" -> {
        countTextView.text = "30개 이상"
    }

    "few" -> {
        countTextView.text = "2개 이상"
    }

    else -> {
        countTextView.text = "1개 이하"
    }
}

@BindingAdapter("color")
fun setColor(
    textView: TextView,
    store: Store
) = when (store.remain_stat) {

    "plenty" -> {
        textView.setTextColor(Color.GREEN)
    }

    "some" -> {
        textView.setTextColor(Color.YELLOW)
    }

    "few" -> {
        textView.setTextColor(Color.RED)
    }

    else -> {
        textView.setTextColor(Color.GRAY)
    }
}
