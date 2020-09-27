package com.amk.weatherforall.services

import android.content.SharedPreferences
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.WeatherPresenter.UNITS
import com.amk.weatherforall.core.interfaces.OpenWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherRequestRetrofit {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var openWeather: OpenWeather

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeather = retrofit.create(OpenWeather::class.java)
    }

    fun requestWeather(city: City, keyApi: String) {
        openWeather.loadWeather(city.name, keyApi, UNITS)
            .enqueue(object : Callback<WeatherForecast> {

                override fun onResponse(
                    call: Call<WeatherForecast>,
                    response: Response<WeatherForecast>
                ) {
                    response.body()?.let {
                        WeatherPresenter.weatherForecast = it
                        WeatherPresenter.isRequestSuccessful = true
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                    WeatherPresenter.isRequestSuccessful = false
                }
            })
    }


}