package com.amk.weatherforall.core.weather.weatherRequest

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.WeatherPresenter
import java.net.URL


class WeatherRequestCityName(val city: City): AbstractRequest() {
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
        return URL(String.format(WEATHER_URL_CITY_EN_FORECAST,
                city.name,WeatherPresenter.KEI_API))
    }


}
