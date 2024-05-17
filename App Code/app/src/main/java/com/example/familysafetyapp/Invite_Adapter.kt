package com.example.familysafetyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
//import com.d4d5.myfamily.databinding.ItemInviteBinding

class Invite_Adapter(private val listContacts: List<ContactModel>) :
    RecyclerView.Adapter<Invite_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Invite_Adapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.items_invite, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: Invite_Adapter.ViewHolder, position: Int) {

        val item = listContacts[position]
        holder.name.text =  item.name
    }

    override fun getItemCount(): Int {
        return listContacts.size
    }

    class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        val name = item.findViewById<TextView>(R.id.name)   //check for member name

    }
}
