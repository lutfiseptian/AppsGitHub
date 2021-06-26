package com.example.appstest.Adapter.AdapterUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appstest.Models.DataItem
import com.example.appstest.Models.ItemsItem
import com.example.appstest.R
import kotlinx.android.synthetic.main.item_issue.view.*
import kotlinx.android.synthetic.main.item_repositori.view.*
import kotlinx.android.synthetic.main.item_repositori.view.tvUsernameRepo


class AdapterRepositori(private var list: List<DataItem>) :
    RecyclerView.Adapter<AdapterRepositori.itemHolderRepo>(){
    inner class itemHolderRepo(itemView: View) : RecyclerView.ViewHolder(itemView)
    fun updateList(list: List<DataItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRepositori.itemHolderRepo {
        return itemHolderRepo(
            LayoutInflater.from(parent.context).inflate(R.layout.item_repositori ,parent , false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterRepositori.itemHolderRepo, position: Int) {
        val item = list[position]
        holder.itemView.apply {
            tvUsernameRepo.text = item.fullName.toString()
            tvid.text = item.id.toString()
            tvDilihatRepo.text = item.watchers.toString()
            tvBahasaRepo.text = item.language.toString()
            tvRepository.text = item.size.toString()
            Glide.with(holder.itemView)
                .load(item.owner?.avatarUrl)
                .into(holder.itemView.imageUserRepo)
        }
    }

}