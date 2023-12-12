package com.doma.sp.doma.book_info

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.doma.sp.doma.R
import com.doma.sp.doma.data.BookInfo
import com.doma.sp.doma.databinding.ActivityBookInfoBinding

class BookInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBookInfoBinding.inflate(layoutInflater) }

    companion object {
        //나중에 위치 받아서 값 설정하기
        // 위치 사용될 변수
        lateinit var BookData: BookInfo
        fun bookInfoIntent(context: Context?, bookData: BookInfo) =
            Intent(context, BookInfoActivity::class.java).apply {
                BookData = bookData
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        readBookInfo()

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun readBookInfo() = with(binding) {

        Glide.with(this@BookInfoActivity).load(BookData.thumbnail).error(R.drawable.ic_book).into(ivBookThumbnail)

        tvTitle.text = BookData.title
        tvAuthorData.text = BookData.authors.toString()
        tvPublisherData.text = BookData.publisher
        tvDatetimeData.text = BookData.datetime
        tvPriceData.text = BookData.price.toString()
        tvContents.text = BookData.contents
    }
}