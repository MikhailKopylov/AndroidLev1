package com.amk.weatherforall.fragments.mainFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData
import com.amk.weatherforall.services.DateTimeUtils
import com.amk.weatherforall.services.DateTimeUtils.isChangeTimeOfDay
import com.amk.weatherforall.services.DateTimeUtils.isNextDay
import com.amk.weatherforall.services.Settings
import com.amk.weatherforall.services.converterToMmHg
import com.amk.weatherforall.services.drawable


class  ListWeatherAdapter(
    private val weathersList: Array<WeatherData>
) : RecyclerView.Adapter<WeatherHolder>() {

    companion object {
        const val DATE_HEADER_ITEM_TYPE = 0
        const val WEATHER_ITEM_TYPE = 1
    }

    private val listHeaderDate: MutableSet<Int> = mutableSetOf()
    private val weatherListWithDateHeader: Array<WeatherData> = addHeaderDate()

    private fun addHeaderDate(): Array<WeatherData> {
        var weatherListWithDateHeader = emptyArray<WeatherData>()
        weathersList.forEachIndexed { index, element ->
            run {
                if (index > 0) {
                    if (isNextDay(element.getDateTime(), weathersList[index - 1].getDateTime())) {
                        weatherListWithDateHeader += weathersList[index]
                        listHeaderDate.add(weatherListWithDateHeader.size - 1)
                    }
                } else {
                    weatherListWithDateHeader += weathersList[index]
                    listHeaderDate.add(weatherListWithDateHeader.size - 1)
                }
                weatherListWithDateHeader += weathersList[index]
            }
        }
        return weatherListWithDateHeader
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return when (viewType) {
            WEATHER_ITEM_TYPE -> {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_weather, parent, false)
                WeatherItem(view)
            }
            DATE_HEADER_ITEM_TYPE -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date, parent, false)
                DateItem(view)
            }
            else -> throw NoSuchElementException("No such type item")
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.updateItem(position)
    }

    override fun getItemCount(): Int {
        return weatherListWithDateHeader.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listHeaderDate.contains(position)) {
            DATE_HEADER_ITEM_TYPE
        } else {
            WEATHER_ITEM_TYPE
        }
    }

    //    interface onWeatherItemClickListener {
//        fun onItemClickListener(view: View, position: Int)
//    }


    inner class WeatherItem(itemView: View) : WeatherHolder(itemView) {

        private val temperatureTextView: TextView =
            itemView.findViewById(R.id.temperature_text_view)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_text_view)
        private val timeTextView: TextView = itemView.findViewById(R.id.time_text_view)
        private val windTextView: TextView = itemView.findViewById(R.id.wind_textView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description_textView)
        private val pressureTextView: TextView = itemView.findViewById(R.id.pressure_textView)
        private val iconWeather: ImageView = itemView.findViewById(R.id.weather_image_view)
        private val cardView: CardView = itemView.findViewById(R.id.weather_cardview)

//        init {
//            itemView.setOnClickListener {
//                if (it != null) {
//                    itemClickListener.onItemClickListener(itemView, adapterPosition)
//                }
//            }
//        }

        override fun updateItem(position: Int) {
            temperatureTextView.text =
                Settings.temperatureMode(Settings.temperatureC, weatherListWithDateHeader[position])
            pressureView(weatherListWithDateHeader[position])
            windView(weatherListWithDateHeader[position])
            dateView(weatherListWithDateHeader[position])
            timeView(weatherListWithDateHeader[position])
            iconView(weatherListWithDateHeader[position])
            descriptionView(weatherListWithDateHeader[position])

           // setMarginTop(weatherListWithDateHeader[position])
        }

        private fun setMarginTop(weatherData: WeatherData) {
            if (isChangeTimeOfDay(weatherData.getDateTime())) {
                val cardViewMarginParams:ViewGroup.MarginLayoutParams  = cardView.layoutParams as ViewGroup.MarginLayoutParams
                cardViewMarginParams.setMargins(0, 40,0, 0)
                cardView.requestLayout()
            }
        }




        @SuppressLint("SetTextI18n")
        fun windView(weatherData: WeatherData) {
            val wind: String = windTextView.context.resources.getString(R.string.wind)
            val m_s: String = windTextView.context.resources.getString(R.string.m_s)
            if (Settings.showWind) {
                windTextView.text = "$wind ${weatherData.wind.speed} $m_s"
                windTextView.visibility = View.VISIBLE
            } else {
                windTextView.visibility = View.INVISIBLE
            }
        }

        @SuppressLint("SetTextI18n")
        fun pressureView(weatherData: WeatherData) {
            val mm_Hg: String = pressureTextView.context.resources.getString(R.string.mm_Hg)
            val pressure: String = pressureTextView.context.resources.getString(R.string.pressure)
            if (Settings.showPressure) {
                pressureTextView.text =
                    "$pressure ${weatherData.main.pressure.converterToMmHg()} $mm_Hg"
                pressureTextView.visibility = View.VISIBLE
            } else {
                pressureTextView.visibility = View.INVISIBLE
            }
        }

        @SuppressLint("SetTextI18n")
        fun dateView(weatherData: WeatherData) {
            dateTextView.text = "${DateTimeUtils.formatDate(weatherData.getDateTime())} "
        }

        @SuppressLint("SetTextI18n")
        fun timeView(weatherData: WeatherData) {
            timeTextView.text = "${DateTimeUtils.formatTime(weatherData.getDateTime())} "
        }

        @SuppressLint("SetTextI18n")
        fun descriptionView(weatherData: WeatherData) {
            descriptionTextView.text =
                Settings.startWithUpperCase(weatherData.weather[0].description)
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun iconView(weatherData: WeatherData) {
            iconWeather.setImageDrawable(
                iconWeather.context.resources.getDrawable(
                    drawable(weatherData.weather[0].icon),
                    null
                )
            )
        }
    }

    inner class DateItem(itemView: View) : WeatherHolder(itemView) {

        private val dateTextView: TextView = itemView.findViewById(R.id.header_date_text_view)

        override fun updateItem(position: Int) {
            dateView(weatherListWithDateHeader[position])
        }

        @SuppressLint("SetTextI18n")
        fun dateView(weatherData: WeatherData) {
            dateTextView.text = "${DateTimeUtils.formatDate(weatherData.getDateTime())} "
        }
    }

}