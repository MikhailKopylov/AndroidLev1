package com.amk.weatherforall.core.weather.weatherRequest

import com.amk.weatherforall.core.weather.WeatherForecast
import com.amk.weatherforall.core.weather.weatherFor5Days.*
import com.amk.weatherforall.core.WeatherPresenter.city
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.text.ParseException
import javax.net.ssl.HttpsURLConnection

abstract class AbstractRequest {
    private val main: Main = Main(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0.0)
    private val weather: Weather = Weather(0, "", "", "")
    private val clouds: Clouds = Clouds(0)
    private val wind: Wind = Wind(0.0, 0)
    private val sys: Sys = Sys("")
    private val weatherDataDefault: WeatherData =
        WeatherData(1L, main, arrayOf(weather), clouds, wind, 0, 0.0, sys, "")
    private var weatherResult: WeatherForecast = WeatherForecast(city, arrayOf(weatherDataDefault))
        get() {
            if (WeatherRequestCityName.isSendRequest) {
                WeatherRequestCityName.isSendRequest = false
                requestURL()
            }
            return field
        }


    abstract fun url():URL

    private fun requestURL() {
        val uri = url()

        try {
            try {
                val urlConnection: HttpsURLConnection =
                    (uri.openConnection() as HttpsURLConnection)
                urlConnection.requestMethod = "GET"
                urlConnection.connectTimeout = 10_000
                val inStream = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result: String = inStream.use(BufferedReader::readText)
                val gson = Gson()
                weatherResult = gson.fromJson(result, WeatherForecast::class.java)
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }
}