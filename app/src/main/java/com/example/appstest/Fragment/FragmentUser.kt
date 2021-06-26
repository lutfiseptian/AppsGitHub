package com.example.appstest.Fragment

import android.content.ContentValues.TAG
import android.content.Intent
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
import com.example.appstest.Adapter.AdapterUser.AdapterUser
import com.example.appstest.Helper.Config
import com.example.appstest.Models.ItemsItem
import com.example.appstest.Models.ResponseModelUser
import com.example.appstest.R
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.Response

class FragmentUser : Fragment() {


    private var isLoading: Boolean = false
    private var page = 1
    private var totalPage = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterUser: AdapterUser
    private lateinit var layoutManager: LinearLayoutManager
    private val listItem = ArrayList<ItemsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_user)
        layoutManager = LinearLayoutManager(activity)
        adapterUser = AdapterUser(listItem)
        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = adapterUser
        ListUser()
        refreshApp()
        searchUser.addTextChangedListener { 
            if (listItem.isNotEmpty()){
                if (it!!.length > 1){
                    val filterList = ArrayList<ItemsItem>()
                    for (item in listItem) {
                        if (item.login!!.contains(it.toString().toLowerCase())){
                            filterList.add(item)
                        }
                    }
                    isLoading = false
                    adapterUser.updateList(filterList)
                }else{
                    isLoading = totalPage > page
                    adapterUser.updateList(listItem)
                }

            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapterUser.itemCount
                Log.d(TAG, "onScrolled: $isLoading")
                if (visibleItemCount + pastVisibleItem >= total) {
                    Log.d(TAG, "onScrolled: $isLoading")

                    if (isLoading) {
                        isLoading = false
                        Log.d(TAG, "onScrolled: get item")
                        ListUser()
                    }
                }
            }
        })

    }

    private fun ListUser(){
        progressBarSelesai.visibility = View.VISIBLE
        AndroidNetworking.get(Config.UserID())
            .addQueryParameter("page", page.toString())
            .addQueryParameter("per_page", "10")
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndObject(
                ResponseModelUser::class.java,
                object : OkHttpResponseAndParsedRequestListener<ResponseModelUser> {
                    override fun onResponse(
                        okHttpResponse: Response, response: ResponseModelUser
                    ) {
                        if (okHttpResponse.isSuccessful) {

                            progressBarSelesai.visibility = View.GONE
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
                            adapterUser.notifyDataSetChanged()
                        }
                        progressBarSelesai.visibility = View.GONE
                    }
                    override fun onError(anError: ANError?) {
                        progressBarSelesai.visibility = View.GONE
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

    private fun refreshApp() {
        swipeRefresh.setOnRefreshListener {
            listItem.clear()
            adapterUser.notifyDataSetChanged()
            Toast.makeText(activity, "Halaman Di Segarkan", Toast.LENGTH_SHORT).show()
            page = 1
            ListUser()
            if (swipeRefresh.isRefreshing){
                swipeRefresh.isRefreshing = false
            }
        }
    }

    }
