package com.example.reminderpill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.utils.TypeModal

class TypeAdapter(var context: Context , var typeList : ArrayList<TypeModal>) :
    RecyclerView.Adapter<TypeAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtName : TextView = itemView.findViewById(R.id.txtName)
        var imgMedicine : ImageView = itemView.findViewById(R.id.imgMedicine)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.type_item_file , parent , false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtName.text = typeList[position].medicinceName

        Glide.with(context).load(typeList[position].medicinceImg).into(holder.imgMedicine)

    }
}