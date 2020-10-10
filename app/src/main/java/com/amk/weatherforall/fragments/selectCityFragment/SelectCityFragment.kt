package com.amk.weatherforall.fragments.selectCityFragment

import android.Manifest
import android.app.Activity.LOCATION_SERVICE
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.activities.CoordinatorActivity
import com.amk.weatherforall.activities.MapsActivity
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CitySource
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.dialogs.DeleteCityDialog
import com.amk.weatherforall.fragments.dialogs.OnDialogListener
import com.google.android.gms.maps.model.LatLng

class SelectCityFragment : Fragment() {


    private lateinit var publisherWeather: PublisherWeather

    private lateinit var mView: View

    lateinit var recyclerView: RecyclerView
    lateinit var cityListAdapter: CityListAdapter

    private lateinit var citySource: CitySource
    private lateinit var deletingCity: City


    companion object {

        const val REQUEST_CODE_MAPS_COORD = 10001

        fun getInstance(): SelectCityFragment {
            return SelectCityFragment()
        }
    }

    private val onDialogListener: OnDialogListener = object :
        OnDialogListener {
        override fun onDialogOK() {
            citySource.deleteCity(deletingCity)
            cityListAdapter.cityList = citySource.allCities
            cityListAdapter.notifyDataSetChanged()
        }

        override fun onDialogCancel() {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisherWeather = (context as PublisherWeatherGetter).publisherWeather()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        initCityList()

        initRecyclerView(view)

        requestResult(view)
        Log.d("SelectCityActivity", "onCreate")
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.city_list_view)
        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        cityListAdapter = CityListAdapter(citySource.allCities)
        recyclerView.adapter = cityListAdapter

        cityListAdapter.setOnItemClickListener(object :
            CityListAdapter.onCityItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
                requestCityName(citySource.allCities[position].name)
            }

            override fun onItemLongClickListener(view: View, position: Int) {
                deletingCity = citySource.allCities[position]
                val deleteCityDialog: DeleteCityDialog = DeleteCityDialog.newInstance()
                deleteCityDialog.onDialogListener = onDialogListener
                activity?.supportFragmentManager?.let { deleteCityDialog.show(it, "Dialog") }
            }

        })
    }

    private fun initCityList() {
        citySource = CitySource((activity as CoordinatorActivity).db.cityDAO())
        if (citySource.allCities.isEmpty()) {
            citySource.addCity(City("Moscow"))
            citySource.addCity(City("Saint Petersburg"))
            citySource.addCity(City("Saratov"))
        }
    }


    private fun requestResult(view: View) {
        val okButton: Button = view.findViewById(R.id.ok_button_select_city)
        val newCityEditText: EditText = view.findViewById(R.id.writeCityNameEditText)
        okButton.setOnClickListener {
            val newCityName = newCityEditText.text.toString()
            newCityEditText.setText("")
            citySource.addCity(City(newCityName))
            requestCityName(newCityName)
        }

        val selectCoordButton: Button = view.findViewById(R.id.show_on_map_button)
        selectCoordButton.setOnClickListener {
            startActivityForResult(
                Intent(context, MapsActivity::class.java),
                REQUEST_CODE_MAPS_COORD
            )
        }
    }

    private fun requestCityName(cityNameResult: String) {
        WeatherPresenter.city = City(cityNameResult)
        val bundle = Bundle()
        WeatherPresenter.newRequest(city = WeatherPresenter.city)
        (context as AppCompatActivity)
            .supportFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
        if (context is StartFragment) {
            (context as StartFragment).runFragments(FragmentsNames.MainFragment, bundle)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_MAPS_COORD) {
                val latitude: Double =
                    data?.getDoubleExtra(MapsActivity.LATITUDE, WeatherPresenter.LATITUDE_DEFAULT)
                        ?: WeatherPresenter.LATITUDE_DEFAULT
                val longitude: Double =
                    data?.getDoubleExtra(MapsActivity.LONGITUDE, WeatherPresenter.LONGITUDE_DEFAULT)
                        ?: WeatherPresenter.LONGITUDE_DEFAULT
                requestCoord(latitude, longitude)
            }

        }
    }

    private fun requestCoord(latitude: Double, longitude: Double) {
        WeatherPresenter.newRequest(latitude, longitude)
        (context as AppCompatActivity)
            .supportFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
        if (context is StartFragment) {
            (context as StartFragment).runFragments(FragmentsNames.MainFragment, Bundle())
        }
    }
}