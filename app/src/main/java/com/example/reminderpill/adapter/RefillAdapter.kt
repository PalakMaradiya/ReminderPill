package com.example.reminderpill.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.utils.MedicineModal
import com.example.reminderpill.utils.RefillModal

class RefillAdapter(var context: Context, var list: ArrayList<RefillModal>)
    : RecyclerView.Adapter<RefillAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgRefill : ImageView = itemView.findViewById(R.id.imgRefill)
        var txtRefill : TextView = itemView.findViewById(R.id.txtRefill)
        var txtStoke : TextView = itemView.findViewById(R.id.txtStoke)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(context, R.layout.refill_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtRefill.text = list[position].mediName
        holder.txtStoke.text = list[position].stock
        Glide.with(context).load(list[position].mediImg).into(holder.imgRefill)

    }
}