package com.example.reminderpill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.reminderpill.R
import com.example.reminderpill.utils.SliderData

class SliderAdapter(val context: Context,
                    val sliderList: ArrayList<SliderData>) : PagerAdapter() {
    override fun getCount(): Int {
        return sliderList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = layoutInflater.inflate(R.layout.first_item_file, container, false)
        val imageView: ImageView = view.findViewById(R.id.img)
        val txtInfo: TextView = view.findViewById(R.id.txtInfo)
        val txt: TextView = view.findViewById(R.id.txt)

        val sliderData: SliderData = sliderList.get(position)
        txtInfo.text = sliderData.slideTitle
        txt.text = sliderData.slideDescription
        imageView.setImageResource(sliderData.slideImage)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}