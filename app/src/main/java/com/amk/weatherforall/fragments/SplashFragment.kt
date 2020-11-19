package com.amk.weatherforall.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.amk.weatherforall.R
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CityDatabase
import com.amk.weatherforall.core.database.CitySource
import com.amk.weatherforall.core.interfaces.FragmentWeather
import com.amk.weatherforall.core.weather.WeatherForecast

class SplashFragment : Fragment(), FragmentWeather {
    private lateinit var citySource: CitySource

    override fun onAttach(context: Context) {
        super.onAttach(context)
        WeatherPresenter.fragment = this
        val db: CityDatabase? = activity?.applicationContext?.let {
            Room.databaseBuilder<CityDatabase>(
                it, CityDatabase::class.java, "city_database"
            ).allowMainThreadQueries()
                .build()
        }
        if (db != null) {
            citySource = CitySource(cityDAO = db.cityDAO())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spalsh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WeatherPresenter.newRequest(WeatherPresenter.city, resources.getString(R.string.Local))
    }

    companion object {
        @JvmStatic
        fun getInstance() =  SplashFragment()
    }

    override fun updateWeather(weatherForecast: WeatherForecast?) {
        runFragments(activity ?: return, FragmentsNames.MainFragment)
    }
}