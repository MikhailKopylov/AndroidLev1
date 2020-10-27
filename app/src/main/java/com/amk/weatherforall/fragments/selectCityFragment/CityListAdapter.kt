package com.amk.weatherforall.fragments.selectCityFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City

class CityListAdapter(var cityList: List<City>) :
    RecyclerView.Adapter<CityListAdapter.CityListHolder>() {

    lateinit var itemClickListener: OnCityItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnCityItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city_list, parent, false)
        return CityListHolder(view)
    }

    override fun onBindViewHolder(holder: CityListHolder, position: Int) {
        holder.cityNameTextView.text = cityList[position].name
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    inner class CityListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cityNameTextView: TextView = itemView.findViewById(R.id.city_name_text_view)

        init {
            itemView.setOnClickListener {
                if (it != null) {
                    itemClickListener.onItemClickListener(itemView, adapterPosition)
                }
            }
            itemView.setOnLongClickListener {
                if (it != null) {
                    itemClickListener.onItemLongClickListener(itemView, adapterPosition)
                }
                true
            }
        }
    }

    interface OnCityItemClickListener {
        fun onItemClickListener(view: View, position: Int)
        fun onItemLongClickListener(view: View, position: Int)
    }

}