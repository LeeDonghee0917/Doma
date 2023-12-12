package com.doma.sp.doma.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.doma.sp.doma.book_info.BookInfoActivity

fun navigateToBookInfoActivity(context: Context) {
    val intent = Intent(context, BookInfoActivity::class.java)
    context.startActivity(intent)
}

// 토스트 메시지 함수
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}