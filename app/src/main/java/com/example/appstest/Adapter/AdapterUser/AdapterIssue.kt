package com.example.appstest.Adapter.AdapterUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appstest.Models.ItemsItem
import com.example.appstest.Models.ItemsItemIssue
import com.example.appstest.R
import kotlinx.android.synthetic.main.item_issue.view.*
import kotlinx.android.synthetic.main.item_repositori.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterIssue(private var list: List<ItemsItemIssue>) :
        RecyclerView.Adapter<AdapterIssue.itemHolderIssue>(){
    inner class itemHolderIssue(itemView: View) : RecyclerView.ViewHolder(itemView)
    fun updateList(listIssue: List<ItemsItemIssue>){
        this.list = listIssue
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterIssue.itemHolderIssue {
        return itemHolderIssue(
                LayoutInflater.from(parent.context).inflate(R.layout.item_issue ,parent , false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterIssue.itemHolderIssue, position: Int) {
        val item = list[position]
        holder.itemView.apply {
            tvUsernameIssue.text = item.user?.login.toString()
            tvTanggalIssue.text = item.createdAt.toString()
            tvMasalahTerbuka.text = item.comments.toString()
            tvTingkat.text = item.score.toString()
            Glide.with(holder.itemView)
                .load(item.user?.avatarUrl)
                .into(holder.itemView.imageUserIssue)
        }

    }

}