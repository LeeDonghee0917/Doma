package com.doma.sp.doma.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

data class BookJson(
    val documents: List<Document>, // 응답 결과
    val meta: Meta // 응답 관련 정보
)

@Serializable
data class Document (
    val authors: List<String>, // 저자 리스트
    val contents: String, // 도서 소개
    val datetime: Date, // 도서 출판날짜
    val isbn: String, // 국제 표준 도서번호
    val price: Int, // 정가
    val publisher: String, // 출판사

    @SerialName("sale_price")
    val salePrice: Int, // 판매가

    val status: String, // 판매 상태(정상, 품절, 절판)
    val thumbnail: String, // 표지 미리보기
    val title: String, //도서 제목
    val translators: List<String>, // 번역자 리스트
    val url: String // 도서 상세 URL
)

@Serializable
data class Meta (
    @SerialName("is_end")
    val isEnd: Boolean, //현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음

    @SerialName("pageable_count")
    val pageableCount: Int, //중복된 문서를 제외하고, 처음부터 요청 페이지까지의 노출 가능 문서 수

    @SerialName("total_count")
    val totalCount: Int // 검색된 문서 수
)
