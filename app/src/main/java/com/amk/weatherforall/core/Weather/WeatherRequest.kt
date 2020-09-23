package com.amk.weatherforall.core.Weather

import android.os.Handler
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.City.Coord
import com.amk.weatherforall.core.Weather.weatherFor5Days.*
import com.amk.weatherforall.core.Weather.weatherFor5Days.Wind
import com.amk.weatherforall.fragments.MainFragment
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

class WeatherRequest(val fragment: MainFragment) {
    companion object {
        var isSendRequest: Boolean = true
        const val TAG: String = "WEATHER"
        const val WEATHER_URL_COORD: String =
            "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid="
        const val WEATHER_URL_ID: String =
            "https://api.openweathermap.org/data/2.5/weather?id=524901&appid="
        const val WEATHER_URL_NAME: String =
            "https://api.openweathermap.org/data/2.5/weather?q=Moscow,RU&appid="

        const val WEATHER_URL_COORD_FORECAST: String =
            "https://api.openweathermap.org/data/2.5/forecast?q=Saint Petersburg&units=metric&APPID="
//        "https://api.openweathermap.org/data/2.5/forecast/daily?q=Moscow&cnt=7&units=metric&appid="
//        "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid="
//        "https://api.openweathermap.org/data/2.5/forecast?q=Moscow&APPID="
        const val WEATHER_URL_COORD_FORECAST_WRONG =
            "https://api.openweathermap.org/data/2.5/forecast?q=Soratovas&units=metric&APPID="
        const val API_KEY: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"

    }

    val city: City = /*CitiesList.citiesMap["Moscow"] ?:*/ City(
        0,
        "Saratov",
        Coord(46.033333, 51.566666),
        "RU",
        ""
    )
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
                    val i: Int = 0
                    handler.post {
                        print(weatherResult2)
                        weatherResult = weatherResult2
                        fragment.updateWeather(weatherResult)
                    }
                } catch (e: SocketTimeoutException) {
                    fragment.updateWeather(null)
                    e.printStackTrace()
                } catch (e: ParseException) {
                    fragment.updateWeather(null)
                    e.printStackTrace()
                } catch (e: Exception) {
                    fragment.updateWeather(null)
                    e.printStackTrace()
                }

            }.start()

        } catch (e: MalformedURLException) {
            fragment.updateWeather(null)
            e.printStackTrace()
        }
    }

//    fun request(): WeatherForecast {
//        requestURL()
//        return weatherResult
//    }
}


//{"cod":"200","message":0,"cnt":40,"list":[{"dt":1600560000,"main":{"temp":8.85,"feels_like":2.46,"temp_min":8.24,"temp_max":8.85,"pressure":1008,"sea_level":1008,"grnd_level":993,"humidity":74,"temp_kf":0.61},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02n"}],"clouds":{"all":17},"wind":{"speed":7.38,"deg":303},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-20 00:00:00"},{"dt":1600570800,"main":{"temp":7.93,"feels_like":1.48,"temp_min":7.51,"temp_max":7.93,"pressure":1009,"sea_level":1009,"grnd_level":993,"humidity":79,"temp_kf":0.42},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":42},"wind":{"speed":7.47,"deg":297},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-20 03:00:00"},{"dt":1600581600,"main":{"temp":11.22,"feels_like":4.34,"temp_min":11.22,"temp_max":11.34,"pressure":1009,"sea_level":1009,"grnd_level":994,"humidity":64,"temp_kf":-0.12},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":26},"wind":{"speed":8.12,"deg":298},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-20 06:00:00"},{"dt":1600592400,"main":{"temp":12.95,"feels_like":4.88,"temp_min":12.95,"temp_max":12.98,"pressure":1010,"sea_level":1010,"grnd_level":994,"humidity":53,"temp_kf":-0.03},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":28},"wind":{"speed":9.54,"deg":312},"visibility":10000,"pop":0.87,"rain":{"3h":0.52},"sys":{"pod":"d"},"dt_txt":"2020-09-20 09:00:00"},{"dt":1600603200,"main":{"temp":12.96,"feels_like":5.04,"temp_min":12.96,"temp_max":12.96,"pressure":1012,"sea_level":1012,"grnd_level":996,"humidity":50,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02d"}],"clouds":{"all":14},"wind":{"speed":9.11,"deg":322},"visibility":10000,"pop":0.68,"sys":{"pod":"d"},"dt_txt":"2020-09-20 12:00:00"},{"dt":1600614000,"main":{"temp":10.53,"feels_like":3.19,"temp_min":10.53,"temp_max":10.53,"pressure":1013,"sea_level":1013,"grnd_level":997,"humidity":59,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":68},"wind":{"speed":8.31,"deg":326},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-20 15:00:00"},{"dt":1600624800,"main":{"temp":8.28,"feels_like":2.7,"temp_min":8.28,"temp_max":8.28,"pressure":1016,"sea_level":1016,"grnd_level":1000,"humidity":66,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],"clouds":{"all":44},"wind":{"speed":5.66,"deg":318},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-20 18:00:00"},{"dt":1600635600,"main":{"temp":7.68,"feels_like":1.98,"temp_min":7.68,"temp_max":7.68,"pressure":1017,"sea_level":1017,"grnd_level":1001,"humidity":72,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":1},"wind":{"speed":5.98,"deg":307},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-20 21:00:00"},{"dt":1600646400,"main":{"temp":7.01,"feels_like":0.73,"temp_min":7.01,"temp_max":7.01,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":72,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":3},"wind":{"speed":6.66,"deg":305},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-21 00:00:00"},{"dt":1600657200,"main":{"temp":7.15,"feels_like":1.15,"temp_min":7.15,"temp_max":7.15,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":70,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":33},"wind":{"speed":6.19,"deg":289},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-21 03:00:00"},{"dt":1600668000,"main":{"temp":10.68,"feels_like":4.43,"temp_min":10.68,"temp_max":10.68,"pressure":1020,"sea_level":1020,"grnd_level":1004,"humidity":57,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":65},"wind":{"speed":6.66,"deg":290},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-21 06:00:00"},{"dt":1600678800,"main":{"temp":13.4,"feels_like":6.94,"temp_min":13.4,"temp_max":13.4,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":49,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":98},"wind":{"speed":7.06,"deg":287},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-21 09:00:00"},{"dt":1600689600,"main":{"temp":14.67,"feels_like":8.34,"temp_min":14.67,"temp_max":14.67,"pressure":1018,"sea_level":1018,"grnd_level":1002,"humidity":48,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":96},"wind":{"speed":7.1,"deg":282},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-21 12:00:00"},{"dt":1600700400,"main":{"temp":13.23,"feels_like":8.32,"temp_min":13.23,"temp_max":13.23,"pressure":1018,"sea_level":1018,"grnd_level":1002,"humidity":55,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":100},"wind":{"speed":5.24,"deg":282},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-21 15:00:00"},{"dt":1600711200,"main":{"temp":11.46,"feels_like":6.12,"temp_min":11.46,"temp_max":11.46,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":62,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":55},"wind":{"speed":5.87,"deg":286},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-21 18:00:00"},{"dt":1600722000,"main":{"temp":10.97,"feels_like":5.57,"temp_min":10.97,"temp_max":10.97,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":67,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":80},"wind":{"speed":6.13,"deg":290},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-21 21:00:00"},{"dt":1600732800,"main":{"temp":10.44,"feels_like":5.38,"temp_min":10.44,"temp_max":10.44,"pressure":1020,"sea_level":1020,"grnd_level":1004,"humidity":73,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":90},"wind":{"speed":5.86,"deg":295},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-22 00:00:00"},{"dt":1600743600,"main":{"temp":9.64,"feels_like":5.4,"temp_min":9.64,"temp_max":9.64,"pressure":1020,"sea_level":1020,"grnd_level":1004,"humidity":80,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":88},"wind":{"speed":4.85,"deg":292},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-22 03:00:00"},{"dt":1600754400,"main":{"temp":13.64,"feels_like":9.7,"temp_min":13.64,"temp_max":13.64,"pressure":1021,"sea_level":1021,"grnd_level":1005,"humidity":66,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":51},"wind":{"speed":4.76,"deg":275},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-22 06:00:00"},{"dt":1600765200,"main":{"temp":17.01,"feels_like":12.51,"temp_min":17.01,"temp_max":17.01,"pressure":1020,"sea_level":1020,"grnd_level":1005,"humidity":53,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":91},"wind":{"speed":5.55,"deg":271},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-22 09:00:00"},{"dt":1600776000,"main":{"temp":18.29,"feels_like":13.51,"temp_min":18.29,"temp_max":18.29,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":49,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":77},"wind":{"speed":5.96,"deg":268},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-22 12:00:00"},{"dt":1600786800,"main":{"temp":15.95,"feels_like":12.38,"temp_min":15.95,"temp_max":15.95,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":59,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":92},"wind":{"speed":4.41,"deg":259},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-22 15:00:00"},{"dt":1600797600,"main":{"temp":14.67,"feels_like":10.15,"temp_min":14.67,"temp_max":14.67,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":61,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":95},"wind":{"speed":5.54,"deg":266},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-22 18:00:00"},{"dt":1600808400,"main":{"temp":13.8,"feels_like":9.05,"temp_min":13.8,"temp_max":13.8,"pressure":1018,"sea_level":1018,"grnd_level":1003,"humidity":60,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":93},"wind":{"speed":5.53,"deg":269},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-22 21:00:00"},{"dt":1600819200,"main":{"temp":13.11,"feels_like":8.44,"temp_min":13.11,"temp_max":13.11,"pressure":1018,"sea_level":1018,"grnd_level":1002,"humidity":66,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":83},"wind":{"speed":5.64,"deg":281},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-23 00:00:00"},{"dt":1600830000,"main":{"temp":12.01,"feels_like":7.4,"temp_min":12.01,"temp_max":12.01,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":75,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":1},"wind":{"speed":5.83,"deg":287},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-23 03:00:00"},{"dt":1600840800,"main":{"temp":15.43,"feels_like":11.93,"temp_min":15.43,"temp_max":15.43,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":68,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":10},"wind":{"speed":4.9,"deg":296},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-23 06:00:00"},{"dt":1600851600,"main":{"temp":19.21,"feels_like":15.7,"temp_min":19.21,"temp_max":19.21,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":54,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":0},"wind":{"speed":4.95,"deg":303},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-23 09:00:00"},{"dt":1600862400,"main":{"temp":20.24,"feels_like":17.18,"temp_min":20.24,"temp_max":20.24,"pressure":1018,"sea_level":1018,"grnd_level":1003,"humidity":50,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":0},"wind":{"speed":4.23,"deg":315},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-23 12:00:00"},{"dt":1600873200,"main":{"temp":17.06,"feels_like":15.11,"temp_min":17.06,"temp_max":17.06,"pressure":1019,"sea_level":1019,"grnd_level":1003,"humidity":62,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":10},"wind":{"speed":2.75,"deg":343},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-23 15:00:00"},{"dt":1600884000,"main":{"temp":14.78,"feels_like":13.23,"temp_min":14.78,"temp_max":14.78,"pressure":1020,"sea_level":1020,"grnd_level":1004,"humidity":70,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":6},"wind":{"speed":2.03,"deg":349},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-23 18:00:00"},{"dt":1600894800,"main":{"temp":13.47,"feels_like":12.06,"temp_min":13.47,"temp_max":13.47,"pressure":1021,"sea_level":1021,"grnd_level":1005,"humidity":75,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":0},"wind":{"speed":1.75,"deg":8},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-23 21:00:00"},{"dt":1600905600,"main":{"temp":12.47,"feels_like":11.17,"temp_min":12.47,"temp_max":12.47,"pressure":1021,"sea_level":1021,"grnd_level":1006,"humidity":82,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02n"}],"clouds":{"all":20},"wind":{"speed":1.73,"deg":44},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-24 00:00:00"},{"dt":1600916400,"main":{"temp":11.7,"feels_like":10.14,"temp_min":11.7,"temp_max":11.7,"pressure":1023,"sea_level":1023,"grnd_level":1007,"humidity":84,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":95},"wind":{"speed":1.95,"deg":54},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-24 03:00:00"},{"dt":1600927200,"main":{"temp":14.27,"feels_like":12.52,"temp_min":14.27,"temp_max":14.27,"pressure":1023,"sea_level":1023,"grnd_level":1007,"humidity":72,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":97},"wind":{"speed":2.29,"deg":82},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-24 06:00:00"},{"dt":1600938000,"main":{"temp":17.56,"feels_like":16.32,"temp_min":17.56,"temp_max":17.56,"pressure":1023,"sea_level":1023,"grnd_level":1007,"humidity":60,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":37},"wind":{"speed":1.72,"deg":114},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-24 09:00:00"},{"dt":1600948800,"main":{"temp":19.02,"feels_like":17.73,"temp_min":19.02,"temp_max":19.02,"pressure":1023,"sea_level":1023,"grnd_level":1007,"humidity":54,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":48},"wind":{"speed":1.72,"deg":119},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2020-09-24 12:00:00"},{"dt":1600959600,"main":{"temp":17.33,"feels_like":14.67,"temp_min":17.33,"temp_max":17.33,"pressure":1023,"sea_level":1023,"grnd_level":1007,"humidity":55,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":70},"wind":{"speed":3.21,"deg":97},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-24 15:00:00"},{"dt":1600970400,"main":{"temp":14.84,"feels_like":12,"temp_min":14.84,"temp_max":14.84,"pressure":1024,"sea_level":1024,"grnd_level":1008,"humidity":61,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":73},"wind":{"speed":3.18,"deg":103},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-24 18:00:00"},{"dt":1600981200,"main":{"temp":13.3,"feels_like":11.49,"temp_min":13.3,"temp_max":13.3,"pressure":1024,"sea_level":1024,"grnd_level":1008,"humidity":65,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":74},"wind":{"speed":1.54,"deg":101},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2020-09-24 21:00:00"}],"city":{"id":498677,"name":"Saratov","coord":{"lat":51.5667,"lon":46.0333},"country":"RU","population":863725,"timezone":14400,"sunrise":1600569544,"sunset":1600613970}}
