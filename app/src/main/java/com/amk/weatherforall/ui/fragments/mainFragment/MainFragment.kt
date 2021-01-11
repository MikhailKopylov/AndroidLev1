package com.amk.weatherforall.ui.fragments.mainFragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.amk.weatherforall.R
import com.amk.weatherforall.mvp.model.WeatherRequestRetrofitRx
import com.amk.weatherforall.mvp.model.api.WeatherApiHolderCity
import com.amk.weatherforall.mvp.model.api.WeatherApiHolderCoord
import com.amk.weatherforall.mvp.model.database.CityDatabase
import com.amk.weatherforall.mvp.model.database.CitySource
import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast
import com.amk.weatherforall.mvp.presenter.WeatherPresenter
import com.amk.weatherforall.mvp.view.WeatherView
import com.amk.weatherforall.ui.fragments.dialogs.OnDialogReconnectListener
import com.amk.weatherforall.viewModels.SelectCityViewModel
import com.amk.weatherforall.viewModels.SettingViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class MainFragment : Fragment(), WeatherView {


    companion object {
        fun getInstance(/*cityNameView: CityName*/): MainFragment {
            val mainFragment = MainFragment()
//            mainFragment.cityNameView = cityNameView
            return MainFragment()
        }

        const val LOAD_DATA: String = "Load data..."
        const val LOAD_DATA_ID: Int = -1
    }

    private var city: City = City(LOAD_DATA, LOAD_DATA_ID)
    private lateinit var citySource: CitySource

    private lateinit var weatherForecast: WeatherForecast
    private lateinit var weatherPresenter: WeatherPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadDataTextView: TextView

    private lateinit var modelCity: SelectCityViewModel
//    lateinit var cityNameView: CityName

    private lateinit var changeSettings: SettingViewModel


    private val onDialogReconnectListener: OnDialogReconnectListener =
        object : OnDialogReconnectListener {
            override fun onDialogReconnect() {
                city = weatherPresenter.city
                weatherPresenter.newRequest(city, resources.getString(R.string.Local))
            }

            override fun onDialogCancel() {

            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDB()
    }

    private fun initDB() {
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initNotificationChannel()

    }

    private fun initPresenter() {
        weatherPresenter = WeatherPresenter(
            this,
            WeatherRequestRetrofitRx(
                WeatherApiHolderCoord.dataSourceCoord,
                WeatherApiHolderCity.dataSourceCityName
            ),
            AndroidSchedulers.mainThread(),
            citySource
        )
        weatherPresenter.firstLoad(resources.getString(R.string.Local))
    }

    override fun onPause() {
        super.onPause()
        weatherPresenter.onPause(weatherForecast)
    }

    override fun setWeather(weatherForecast: WeatherForecast) {
        view?.let {
            recyclerView = it.findViewById(R.id.nextWeather_view)
            loadDataTextView = it.findViewById(R.id.load_data_text_view)
            recyclerView.layoutManager = LinearLayoutManager(it.context)
            recyclerView.adapter = ListWeatherAdapter(weatherForecast.list, resources)
        }
    }

    override fun setCity(city: City) {
        this.city = city
    }

    override fun setErrorMessage(error: String) {
        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    override fun successFirstLoad() {
        recyclerView.visibility = View.VISIBLE
        loadDataTextView.visibility = View.GONE

    }


    // На Андроидах версии 26 и выше необходимо создавать канал уведомлений
    // На старых версиях канал создавать не надо
    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(
                "2", "name", importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}