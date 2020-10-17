package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData
import com.amk.weatherforall.services.Settings


class NextWeatherAdapter(
    private var nextWeathersList: Array<WeatherData>
) : RecyclerView.Adapter<NextWeatherAdapter.NextWeatherHolder>() {

    lateinit var itemClickListener:onWeatherItemClickListener


    fun setOnItemClickListener(onItemClickListener: onWeatherItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextWeatherHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_next_weather, parent, false)
        return NextWeatherHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NextWeatherHolder, position: Int) {
        holder.temperatureTextView.text = Settings.temperatureMode(Settings.temperatureC, nextWeathersList[position])

        Settings.pressureMode(holder.pressureTextView,nextWeathersList[position])

        Settings.windMode(holder.windTextView, nextWeathersList[position])

    }

    override fun getItemCount(): Int {
        return nextWeathersList.size
    }

    inner class NextWeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var temperatureTextView: TextView = itemView.findViewById(R.id.temperature_text_view)
        var dateTextView:TextView = itemView.findViewById(R.id.date_text_view)
        var windTextView:TextView = itemView.findViewById(R.id.wind_textView)
        var pressureTextView:TextView = itemView.findViewById(R.id.pressure_textView)

        init{
            itemView.setOnClickListener {
                if(it != null){
                    itemClickListener.onItemClickListener(itemView, adapterPosition)
                }
            }
        }
    }

    interface onWeatherItemClickListener{
        fun onItemClickListener(view: View, position: Int)
    }
}