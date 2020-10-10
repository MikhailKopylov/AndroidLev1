package com.amk.weatherforall.core.Weather.weatherRequest

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.Weather.weatherFor5Days.*
import com.amk.weatherforall.core.WeatherPresenter
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.text.ParseException
import java.util.*
import javax.net.ssl.HttpsURLConnection


class WeatherRequestCoord(val lat:Double, val lon:Double): AbstractRequest() {
    companion object {
        var isSendRequest: Boolean = false
        const val TAG: String = "WEATHER"
        const val WEATHER_URL_COORD: String =
            "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid="
        const val WEATHER_URL_ID: String =
            "https://api.openweathermap.org/data/2.5/weather?id=524901&appid="
        const val WEATHER_URL_NAME: String =
            "https://api.openweathermap.org/data/2.5/weather?q=Moscow,RU&appid="

        const val WEATHER_URL_CITY_EN_FORECAST: String =
//            "https://api.openweathermap.org/data/2.5/forecast?q=Saint Petersburg&units=metric&APPID="
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&APPID=%s"
        const val WEATHER_URL_COORD_EN_FORECAST: String =
            "https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric&APPID=%s"

        //        "https://api.openweathermap.org/data/2.5/forecast/daily?q=Moscow&cnt=7&units=metric&appid="
//        "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid="
//        "https://api.openweathermap.org/data/2.5/forecast?q=Moscow&APPID="
        const val WEATHER_URL_COORD_FORECAST_WRONG =
            "https://api.openweathermap.org/data/2.5/forecast?q=Soratovas&units=metric&APPID="
//        const val API_KEY: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"

    }

    override fun url(): URL {
        return URL(String.format(WEATHER_URL_COORD_EN_FORECAST,
                lat.toString(), lon.toString(),WeatherPresenter.KEI_API))
    }


}
