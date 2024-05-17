package com.example.familysafetyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Member_Items_Adapter(private val listMembers: List<MemberData>) : RecyclerView.Adapter<Member_Items_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Member_Items_Adapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.members_item_recyclerview, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: Member_Items_Adapter.ViewHolder, position: Int) {
        val item  = listMembers[position]
        holder.member_name.text = item.member_name
    }

    override fun getItemCount(): Int {
        return listMembers.size
    }

    class ViewHolder(private val item : View) : RecyclerView.ViewHolder(item) {

        val member_image = item.findViewById<ImageView>(R.id.member_image)
        val member_name = item.findViewById<TextView>(R.id.member_name)
    }
}