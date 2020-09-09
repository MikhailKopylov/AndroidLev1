package com.amk.weatherforall.core.Weather

import android.os.Handler
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.City.Coord
import com.amk.weatherforall.fragments.MainFragment
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.text.ParseException
import java.util.*
import javax.net.ssl.HttpsURLConnection


//fun main() {
//    val request: WeatherRequest = WeatherRequest(null)
//    request.requestURL()
//}

class WeatherRequest(val fragment: MainFragment) {
    companion object {
        const val TAG: String = "WEATHER"
        const val WEATHER_URL_COORD: String =
            "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid="
        const val WEATHER_URL_ID: String =
            "https://api.openweathermap.org/data/2.5/weather?id=524901&appid="
        const val WEATHER_URL_NAME: String =
            "https://api.openweathermap.org/data/2.5/weather?q=Moscow,RU&appid="

        const val WEATHER_URL_COORD_FORECAST: String =
//            "https://api.openweathermap.org/data/2.5/forecast/daily?q=Moscow&cnt=7&units=metric&appid="
            "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid=appid="
//        "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&lang=zh_cn&appid="

        const val API_KEY: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"

    }

    val city: City = /*CitiesList.citiesMap["Moscow"] ?:*/ City(
        Coord(46.033333, 51.566666),
        498677,
        "Saratov",
        "RU",
        ""
    )
    val temper: Temperature = Temperature(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val weaterDataDefault: WeatherData =
        WeatherData(temper, 0.0, 0, arrayOf(Weather("n d", "n d")), 0.0, 0, 0)
    var weatherResult: WeatherForecast = WeatherForecast(city, arrayOf(weaterDataDefault))
        get() {
            requestURL()
            return field
        }

    fun requestURL() {
        try {
            val uri: URL = URL(WEATHER_URL_COORD_FORECAST + API_KEY)
            val handler: Handler = Handler()
            Thread {
                try {
                    val urlConnection: HttpsURLConnection =
                        (uri.openConnection() as HttpsURLConnection)
                    urlConnection.requestMethod = "GET"
                    urlConnection.connectTimeout = 10_000
                    val inStream: BufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val result: String = inStream.use(BufferedReader::readText)
                    val gson: Gson = Gson()
                    val temp: Date = Date(1599581171)
                    val weatherResult2 = gson.fromJson(result, WeatherForecast::class.java)
                    val i:Int = 0
                    handler.post {
                        print(weatherResult2)
                        weatherResult = weatherResult2
                        fragment.updateWeather(weatherResult)
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }.start()

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }

//    fun request(): WeatherForecast {
//        requestURL()
//        return weatherResult
//    }
    }