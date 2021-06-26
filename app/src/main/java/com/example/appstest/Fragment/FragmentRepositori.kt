package com.example.appstest.Fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener
import com.example.appstest.Adapter.AdapterUser.AdapterRepositori

import com.example.appstest.Helper.Config
import com.example.appstest.Models.DataItem
import com.example.appstest.Models.ItemsItem
import com.example.appstest.Models.ResponseModelRepositori

import com.example.appstest.R
import kotlinx.android.synthetic.main.fragment_repositori.*
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.Response

class FragmentRepositori : Fragment() {


    private var isLoading: Boolean = false
    private var page = 1
    private var totalPage = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRepositori: AdapterRepositori
    private lateinit var layoutManager: LinearLayoutManager
    private val listItem = ArrayList<DataItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repositori, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_repositori)
        layoutManager = LinearLayoutManager(activity)
        adapterRepositori = AdapterRepositori(listItem)
        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = adapterRepositori
        ListRepositori()
        refreshAppRepo()
        searchUserRepo.addTextChangedListener {
            if (listItem.isNotEmpty()){
                if (it!!.length > 1){
                    val filterList = ArrayList<DataItem>()
                    for (item in listItem) {
                        if (item.fullName!!.contains(it.toString().toLowerCase())){
                            filterList.add(item)
                        }
                    }
                    isLoading = false
                    adapterRepositori.updateList(filterList)
                }else{
                    isLoading = totalPage > page
                    adapterRepositori.updateList(listItem)
                }
            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapterRepositori.itemCount
                Log.d(ContentValues.TAG, "onScrolled: $isLoading")
                if (visibleItemCount + pastVisibleItem >= total) {
                    Log.d(ContentValues.TAG, "onScrolled: $isLoading")

                    if (isLoading) {
                        isLoading = false
                        Log.d(ContentValues.TAG, "onScrolled: get item")
                        ListRepositori()
                    }
                }
            }
        })
    }
    private fun ListRepositori(){
        progressBarSelesaiRepo.visibility = View.VISIBLE
        AndroidNetworking.get(Config.RepositoriID())
            .addQueryParameter("page", page.toString())
            .addQueryParameter("per_page", "10")
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndObject(
                ResponseModelRepositori::class.java,
                object : OkHttpResponseAndParsedRequestListener<ResponseModelRepositori> {
                    override fun onResponse(
                        okHttpResponse: Response, response: ResponseModelRepositori
                    ) {
                        if (okHttpResponse.isSuccessful) {
                            progressBarSelesaiRepo.visibility = View.GONE
                            response.incompleteResults
                            if (page == 1){
                                listItem.clear()
                            }
                            response.items?.let {
                                for (i in it.indices) {
                                    listItem.add(it[i]!!.copy())
                                }
                            }
                            totalPage = response.totalCount!!.div(10)
                            if (response.totalCount!!.div(10) > page){
                                page += 1
                                isLoading = true
                            }else{
                                isLoading = false
                            }
                            adapterRepositori.notifyDataSetChanged()
                        }
                        progressBarSelesaiRepo.visibility = View.GONE
                    }
                    override fun onError(anError: ANError?) {
//                        progressBarSelesai.visibility = View.GONE
                        if (anError?.errorCode == 0) {
                        } else {
                            Toast.makeText(activity,
                                "Something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("OrderError", "onError: ${anError?.errorCode}")
                            Log.d("OrderError", "onError: ${anError?.errorBody}")
                            Log.d("OrderError", "onError: ${anError?.errorDetail}")
                        }
                    }
                })
    }

    private fun refreshAppRepo() {
        swipeRefreshRepo.setOnRefreshListener {
            listItem.clear()
            adapterRepositori.notifyDataSetChanged()
            Toast.makeText(activity, "Halaman Di Segarkan", Toast.LENGTH_SHORT).show()
            page = 1
            ListRepositori()
            if (swipeRefreshRepo.isRefreshing){
                swipeRefreshRepo.isRefreshing = false
            }
        }
    }

}


