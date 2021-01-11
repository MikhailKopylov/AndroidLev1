package com.amk.weatherforall.ui.fragments.mainFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun updateItem(position:Int)
}