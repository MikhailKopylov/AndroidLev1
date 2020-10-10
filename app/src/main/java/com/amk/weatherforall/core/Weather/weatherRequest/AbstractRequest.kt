package com.amk.weatherforall.core.Weather.weatherRequest

import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.Weather.weatherFor5Days.*
import com.amk.weatherforall.core.WeatherPresenter.city
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.text.ParseException
import java.util.*
import javax.net.ssl.HttpsURLConnection

abstract class AbstractRequest {
    val temper: Temperature = Temperature(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val main: Main = Main(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0.0)
    val weather: Weather = Weather(0, "", "", "")
    val clouds: Clouds = Clouds(0)
    val wind: Wind = Wind(0.0, 0)
    val sys: Sys = Sys("")
    val weaterDataDefault: WeatherData =
        WeatherData(1L, main, arrayOf(weather), clouds, wind, 0, 0.0, sys, "")
    var weatherResult: WeatherForecast = WeatherForecast(city, arrayOf(weaterDataDefault))
        get() {
            if (WeatherRequestCityName.isSendRequest) {
                WeatherRequestCityName.isSendRequest = false
                requestURL()
            }
            return field
        }


    abstract fun url():URL

    fun requestURL() {
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
                val temp = Date(1599581171)
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