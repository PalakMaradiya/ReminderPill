package com.example.reminderpill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.utils.MedicineModal

class MedicineAdapter(var context: Context, var medicineList: ArrayList<MedicineModal>
,var onItemClick :((img : String) -> Unit )): RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvMedicineName : TextView = itemView.findViewById(R.id.tvMedicineName)
        var imgMedi : ImageView = itemView.findViewById(R.id.imgMedi)
        var contentLayout : LinearLayout = itemView.findViewById(R.id.contentLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.medicine_item_file,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvMedicineName.text = medicineList[position].mediName

        Glide.with(context).load(medicineList[position].mediImg).into(holder.imgMedi)

        holder.contentLayout.setOnClickListener {

            onItemClick.invoke(medicineList[position].mediImg.toString())
        }
    }


    fun addMedicine(medicine: MedicineModal) {
        medicineList.add(medicine)
        notifyItemInserted(medicineList.size - 1)
    }
}