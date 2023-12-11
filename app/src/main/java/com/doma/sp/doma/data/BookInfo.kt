package com.doma.sp.doma.data

data class BookInfo(
    val title: String? = null, // 도서 제목
    val thumbnail: String? = null, // 도서 표지 미리보기 URL
    val contents: String? = null, // 도서 소개
    val isbn: String? = null, // 국제 표준 도서번호
    val url: String? = null, // 도서 상세 URL
    val datetime: String? = null, // 도서 출판날짜
    var publisher: String? = null, // 출판사
    var authors: List<String>? = null, // 저자 리스트
    var price: Int? = null, // 도서 정가
    var sale_price: Int? = null, // 도서 판매가
    var status: String? = null // 판매 상태 정보 (정상, 품절, 절판 등)
)
