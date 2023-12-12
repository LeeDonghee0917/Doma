package com.doma.sp.doma.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.doma.sp.doma.api.ApiKey
import com.doma.sp.doma.data.BookInfo
import com.doma.sp.doma.data.json.BookJson
import com.doma.sp.doma.databinding.FragmentSearchBinding
import com.doma.sp.doma.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var currentQuery: String? = null

    private var s_datas: ArrayList<BookInfo> = ArrayList()

    private val slistAdapter by lazy {
        SearchAdapter(sContext)
    }

    private lateinit var sContext: Context

    private lateinit var smanager: LinearLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setOnClickListener()

        initView()

        return binding.root
    }

    private fun setOnClickListener() {
        setOnQueryTextListener()
    }

    private fun initView() = with(binding) {
        setSearchBookResult()

        smanager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvBookSearch.layoutManager = smanager
        rvBookSearch.adapter = slistAdapter

    }

    fun setSearchBookResult() {
        val query = currentQuery // Get the current query
        if (query.isNullOrEmpty()) {

            return
        }

        RetrofitInstance.api.getDailyList(ApiKey.REST_KEY, query, "latest", 10)
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
                        val searchList = data?.documents
                        if (searchList == null) {// 가져온 데이터 없으면 리턴
                            return
                        } else {
                            for (i in searchList.indices) { // 가져오고 싶은 데이터 불러오고 어뎁터에 저장하는 위치
                                val title = searchList.get(i).title
                                val thumbnail = searchList.get(i).thumbnail
                                var contents = searchList.get(i).contents
                                contents = contents.replace("&#39;", "'")
                                contents = contents.replace("&quot;", "\"")
                                val isbn = searchList.get(i).isbn
                                val url = searchList.get(i).url

                                val datetime = searchList.get(i).datetime

                                val publisher = searchList.get(i).publisher
                                val authors = searchList.get(i).authors
                                val price = searchList.get(i).price
                                val sale_price = searchList.get(i).salePrice
                                val status = searchList.get(i).status

                                s_datas.add(
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
                            s_datas.add(BookInfo())
                            Log.d("r_datas", "$s_datas")
                            slistAdapter.list = s_datas //리스트를 어댑터에 적용
                            slistAdapter.notifyDataSetChanged()// notity
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

    private fun setOnQueryTextListener() {
        binding.svBookSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { // 검색 완료 시
                slistAdapter.clearItem()

                if (!query.isNullOrEmpty()) {
                    currentQuery = query
                    setSearchBookResult()
                    binding.svBookSearch.clearFocus()
                }

                return false // 키보드 내림
            }

            override fun onQueryTextChange(newText: String?): Boolean { // 검색어를 변경할 때마다
                slistAdapter.clearItem()

                currentQuery = newText

                return true // 키보드 안내림
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}