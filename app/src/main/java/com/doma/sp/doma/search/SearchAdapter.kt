package com.doma.sp.doma.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.doma.sp.doma.R
import com.doma.sp.doma.data.BookInfo
import com.doma.sp.doma.databinding.ItemBookListBinding

class SearchAdapter(context: Context) : RecyclerView.Adapter<SearchAdapter.Holder>() {

    var list = ArrayList<BookInfo>()
    var sContext = context

    fun clearItem() {
        list.clear()
        notifyDataSetChanged()
    }

    interface ItemClick {

        fun onClick(view: View, rankData: BookInfo)

    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var item = list[position]
        holder.bind(item)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(val binding: ItemBookListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookInfo) = with(binding) { //클릭이벤트추가부분
            itemView.setOnClickListener {
                itemClick?.onClick(it, item)
            }
            tvTitle.text = item.title
            tvIsbn.text = item.isbn

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_book)
                .error(R.drawable.ic_book)

            Glide.with(sContext).load(item.thumbnail).apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(sivBookThumbnail)

        }
    }
}