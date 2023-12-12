package com.doma.sp.doma.book

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.doma.sp.doma.api.ApiKey
import com.doma.sp.doma.data.BookInfo
import com.doma.sp.doma.data.json.BookJson
import com.doma.sp.doma.databinding.FragmentBookBinding
import com.doma.sp.doma.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    private var q = "" // 검색어

    private var b_datas: ArrayList<BookInfo> = ArrayList()

    private val blistAdapter by lazy {
        BookAdapter(bContext)
    }

    private lateinit var bContext: Context

    private lateinit var brmanager: LinearLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)

        setOnClickListener()

        initView()

        return binding.root
    }

    private fun setOnClickListener() {

    }

    private fun initView() = with(binding) {
        setDailyRank()

        brmanager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvMyPlants.layoutManager = brmanager
        rvMyPlants.adapter = blistAdapter

    }

    fun setDailyRank() {
        RetrofitInstance.api.getDailyList(ApiKey.REST_KEY, q, "latest", 10)
            ?.enqueue(object :
                Callback<BookJson> {
                @SuppressLint("SimpleDateFormat")
                override fun onResponse(call: Call<BookJson>, response: Response<BookJson>) {
                    if (response.isSuccessful) {//응답 성공시 실행

                        val statusCode = response.code()
                        Log.d("Status Code", statusCode.toString())

                        val rawResponse = response.raw().toString()
                        Log.d("Raw Response", rawResponse)

                        val data = response.body()
                        Log.d("data", data.toString())
                        val dailyList = data?.documents
                        Log.d("dailyList", dailyList.toString())
                        if (dailyList == null) {// 가져온 데이터 없으면 리턴
                            return
                        } else {
                            for (i in dailyList.indices) { // 가져오고 싶은 데이터 불러오고 어뎁터에 저장하는 위치
                                val title = dailyList.get(i).title
                                val thumbnail = dailyList.get(i).thumbnail
                                var contents = dailyList.get(i).contents
                                contents = contents.replace("&#39;", "'")
                                contents = contents.replace("&quot;", "\"")
                                val isbn = dailyList.get(i).isbn
                                val url = dailyList.get(i).url

                                val datetime = dailyList.get(i).datetime

                                val publisher = dailyList.get(i).publisher
                                val authors = dailyList.get(i).authors
                                val price = dailyList.get(i).price
                                val sale_price = dailyList.get(i).salePrice
                                val status = dailyList.get(i).status

                                b_datas.add(
                                    BookInfo(
                                        title = title,
                                        thumbnail = thumbnail,
                                        contents = contents,
                                        isbn = isbn,
                                        url = url,
                                        datetime = datetime.toString(),
                                        publisher = publisher,
                                        authors = authors,
                                        price = price.toInt(),
                                        sale_price = sale_price.toInt(),
                                        status = status
                                    )
                                )
                            }
                            b_datas.add(BookInfo())
                            Log.d("r_datas", "$b_datas")
                            blistAdapter.list = b_datas //리스트를 어댑터에 적용
                            blistAdapter.notifyDataSetChanged()// notity
                        }
                    }
                }

                override fun onFailure(call: Call<BookJson>, t: Throwable) {//실패시 찍히는 로그
                    if (t is IOException) { // 네트워크 오류
                        Log.e("Network error", "${t.message}")
                    } else { // 변환 오류
                        Log.e("Conversion issue", "${t.message}")
                    }
                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}