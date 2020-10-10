package com.amk.weatherforall.services

import android.content.SharedPreferences
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.WeatherPresenter.UNITS
import com.amk.weatherforall.core.interfaces.OpenWeatherCityName
import com.amk.weatherforall.core.interfaces.OpenWeatherCoord
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherRequestRetrofit {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var openWeatherCityName: OpenWeatherCityName
    private lateinit var openWeatherCoord: OpenWeatherCoord

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherCityName = retrofit.create(OpenWeatherCityName::class.java)
        openWeatherCoord = retrofit.create(OpenWeatherCoord::class.java)
    }

    fun requestWeatherCity(city: City, keyApi: String){
        openWeatherCityName.loadWeather(city.name, keyApi, UNITS)
            .enqueue(object : Callback<WeatherForecast> {

                override fun onResponse(
                    call: Call<WeatherForecast>,
                    response: Response<WeatherForecast>
                ) {
                    response.body()?.let {
                        WeatherPresenter.isRequestSuccessful = true
                        WeatherPresenter.weatherForecast = it
                        WeatherPresenter.city = it.city
                        WeatherPresenter.fragment.updateWeather(it)
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                    WeatherPresenter.isRequestSuccessful = false
                }
            })

    }

    fun requestWeatherCoord(lat:Double, lon:Double, keyApi: String){
        openWeatherCoord.loadWeather(lat.toString(), lon.toString(), keyApi, UNITS)
            .enqueue(object : Callback<WeatherForecast> {

                override fun onResponse(
                    call: Call<WeatherForecast>,
                    response: Response<WeatherForecast>
                ) {
                    response.body()?.let {
                        WeatherPresenter.weatherForecast = it
                        WeatherPresenter.isRequestSuccessful = true
                        WeatherPresenter.city = it.city
                        WeatherPresenter.fragment.updateWeather(it)
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                    WeatherPresenter.isRequestSuccessful = false
                }
            })

    }


}