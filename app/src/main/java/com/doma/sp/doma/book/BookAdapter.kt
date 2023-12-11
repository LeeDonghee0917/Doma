package com.doma.sp.doma.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doma.sp.doma.data.BookInfo
import com.doma.sp.doma.databinding.ItemBookListBinding

class BookAdapter(context: Context) : RecyclerView.Adapter<BookAdapter.Holder>() {

    var list = ArrayList<BookInfo>()
    var bContext = context

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

//            val requestOptions = RequestOptions()
//                .placeholder(R.drawable.ic_logo) // Placeholder while loading
//                .error(R.drawable.ic_logo)
//
//            Glide.with(bContext).load(item.contentsPhoto).apply(requestOptions)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(ivContentsPoto)

        }
    }
}