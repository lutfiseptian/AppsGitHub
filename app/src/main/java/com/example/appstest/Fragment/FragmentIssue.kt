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
import com.example.appstest.Adapter.AdapterUser.AdapterIssue
import com.example.appstest.Helper.Config
import com.example.appstest.Models.ItemsItem
import com.example.appstest.Models.ItemsItemIssue
import com.example.appstest.Models.ResponseModelIssue
import com.example.appstest.Models.ResponseModelRepositori
import com.example.appstest.R
import kotlinx.android.synthetic.main.fragment_issue.*
import kotlinx.android.synthetic.main.fragment_repositori.*
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.Response


class FragmentIssue : Fragment() {



    private var isLoading: Boolean = false
    private var page = 1
    private var totalPage = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterIssue: AdapterIssue
    private lateinit var layoutManager: LinearLayoutManager
    private val listItem = ArrayList<ItemsItemIssue>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_issue)
        layoutManager = LinearLayoutManager(activity)
        adapterIssue = AdapterIssue(listItem)
        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = adapterIssue
        ListIssue()
        refreshAppIssue()
        searchUserIssue.addTextChangedListener {
            if (listItem.isNotEmpty()){
                if (it!!.length > 1){
                    val filterList = ArrayList<ItemsItemIssue>()
                    for (item in listItem) {
                        Log.d("issue", "onViewCreated: $item")
                        if (item.user?.login!!.contains(it.toString().toLowerCase())){
                            filterList.add(item)
                        }
                    }
                    isLoading = false
                    adapterIssue.updateList(filterList)
                }else{
                    isLoading = totalPage > page
                    adapterIssue.updateList(listItem)
                }

            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapterIssue.itemCount
                Log.d(ContentValues.TAG, "onScrolled: $isLoading")
                if (visibleItemCount + pastVisibleItem >= total) {
                    Log.d(ContentValues.TAG, "onScrolled: $isLoading")

                    if (isLoading) {
                        isLoading = false
                        Log.d(ContentValues.TAG, "onScrolled: get item")
                        ListIssue()
                    }
                }
            }
        })

    }

    private fun ListIssue(){
        progressBarSelesaiIssue.visibility = View.VISIBLE
        AndroidNetworking.get(Config.IssueID())
            .addQueryParameter("page", page.toString())
            .addQueryParameter("per_page", "10")
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndObject(
                ResponseModelIssue::class.java,
                object : OkHttpResponseAndParsedRequestListener<ResponseModelIssue> {
                    override fun onResponse(
                        okHttpResponse: Response, response: ResponseModelIssue
                    ) {
                        if (okHttpResponse.isSuccessful) {
                            Log.d("Fragment", "Connect")
                            response.incompleteResults

                            progressBarSelesaiIssue.visibility = View.GONE
                            if (page == 1){
                                listItem.clear()
                            }
                            response.itemsissue?.let {
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
                            adapterIssue.notifyDataSetChanged()
                        }
                        progressBarSelesaiIssue.visibility = View.GONE
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

    private fun refreshAppIssue() {
        swipeRefreshIssue.setOnRefreshListener {
            listItem.clear()
            adapterIssue.notifyDataSetChanged()
            Toast.makeText(activity, "Halaman Di Segarkan", Toast.LENGTH_SHORT).show()
            page = 1
            ListIssue()
            if (swipeRefreshIssue.isRefreshing){
                swipeRefreshIssue.isRefreshing = false
            }
        }
    }

}