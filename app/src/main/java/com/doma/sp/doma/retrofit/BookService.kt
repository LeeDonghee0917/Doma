package com.doma.sp.doma.retrofit

import com.doma.sp.doma.data.json.BookJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookService {
    @GET("v3/search/book")
    fun getDailyList(
        @Header("Authorization") KakaoAK : String?,
        @Query("query") query: String?,
        @Query("sort") sort: String,
        @Query("size") size: Int,
//        @Query("target") target: String,
    ): Call<BookJson>

    companion object {
        const val RANK_URL = "https://dapi.kakao.com/"
    }
}
/*
    val query: String? = null, // 검색을 원하는 질의어 -> 필수
    val sort: String? = null, // 정렬 방식(accuracy(정확도순) 또는 latest(발간일순))
    val page: Int? = 0, // 결과 페이지 번호(1~50 사이)
    val size: Int? = 0, // 한 페이지에 보여질 문서 수(1~50 사이)
    val target: String? = null, // 검색 필드 제한(title(제목), isbn (ISBN), publisher(출판사), person(인명))
 */