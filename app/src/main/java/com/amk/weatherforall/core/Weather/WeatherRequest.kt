package com.amk.weatherforall.core.Weather

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.weatherFor5Days.*
import com.amk.weatherforall.core.Weather.weatherFor5Days.Wind
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.text.ParseException
import java.util.*
import javax.net.ssl.HttpsURLConnection


//fun main() {
//    val request: WeatherRequest = WeatherRequest(null)
//    request.requestURL()
//}

class WeatherRequest(val city: City) {
    companion object {
        var isSendRequest: Boolean = false
        const val TAG: String = "WEATHER"
        const val WEATHER_URL_COORD: String =
            "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid="
        const val WEATHER_URL_ID: String =
            "https://api.openweathermap.org/data/2.5/weather?id=524901&appid="
        const val WEATHER_URL_NAME: String =
            "https://api.openweathermap.org/data/2.5/weather?q=Moscow,RU&appid="

        const val WEATHER_URL_COORD_FORECAST: String =
//            "https://api.openweathermap.org/data/2.5/forecast?q=Saint Petersburg&units=metric&APPID="
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&APPID=%s"

        //        "https://api.openweathermap.org/data/2.5/forecast/daily?q=Moscow&cnt=7&units=metric&appid="
//        "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid="
//        "https://api.openweathermap.org/data/2.5/forecast?q=Moscow&APPID="
        const val WEATHER_URL_COORD_FORECAST_WRONG =
            "https://api.openweathermap.org/data/2.5/forecast?q=Soratovas&units=metric&APPID="
        const val API_KEY: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"

    }

    //    var city: City = City.CITY_DEFAULT
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
            if (isSendRequest) {
                isSendRequest = false
                requestURL()
            }
            return field
        }

    fun requestURL() {
        try {
            val uri = URL(String.format(WEATHER_URL_COORD_FORECAST, city.name, API_KEY))
            try {
                val urlConnection: HttpsURLConnection =
                    (uri.openConnection() as HttpsURLConnection)
                urlConnection.requestMethod = "GET"
                urlConnection.connectTimeout = 10_000
                val inStream = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result: String = inStream.use(BufferedReader::readText)
                val gson = Gson()
                val temp = Date(1599581171)
                val weatherResult2 = gson.fromJson(result, WeatherForecast::class.java)
                weatherResult = weatherResult2
            } catch (e: SocketTimeoutException) {
//                    fragment.updateWeather(null)
                e.printStackTrace()
            } catch (e: ParseException) {
//                    fragment.updateWeather(null)
                e.printStackTrace()
            } catch (e: Exception) {
//                    fragment.updateWeather(null)
                e.printStackTrace()
            }
        } catch (e: MalformedURLException) {
//            fragment.updateWeather(null)
            e.printStackTrace()
        }
    }
}
