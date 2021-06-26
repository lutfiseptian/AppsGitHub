package com.example.appstest.Adapter.AdapterUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appstest.Models.ItemsItem
import com.example.appstest.R
import kotlinx.android.synthetic.main.item_user.view.*
import kotlin.math.sign

class AdapterUser(private var list: List<ItemsItem>) :
    RecyclerView.Adapter<AdapterUser.itemHolderUser>(){
    inner class itemHolderUser(itemView: View) : RecyclerView.ViewHolder(itemView)
    fun updateList(list: List<ItemsItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUser.itemHolderUser {
      return itemHolderUser(
          LayoutInflater.from(parent.context).inflate(R.layout.item_user ,parent , false)
      )
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: AdapterUser.itemHolderUser, position: Int) {
        val item = list[position]
        holder.itemView.apply {

            tvidUser.text = item.id.toString()
            tvUsername.text = item.login.toString()
            Glide.with(holder.itemView)
                    .load(item.avatarUrl)
                    .into(holder.itemView.imageUser)

        }
    }

}