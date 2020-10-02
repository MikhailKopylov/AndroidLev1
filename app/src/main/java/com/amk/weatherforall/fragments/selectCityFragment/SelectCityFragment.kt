package com.amk.weatherforall.fragments.selectCityFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.activities.CoordinatorActivity
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Constants.CITY_NAME
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.core.database.CitySource
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames
import com.amk.weatherforall.fragments.dialogs.DeleteCityDialog
import com.amk.weatherforall.fragments.dialogs.NoNetworkDialog
import com.amk.weatherforall.fragments.dialogs.OnDialogListener

class SelectCityFragment : Fragment() {

    private lateinit var publisherWeather: PublisherWeather

    private lateinit var mView: View

    lateinit var recyclerView: RecyclerView
    lateinit var  cityListAdapter:CityListAdapter

    private lateinit var citySource: CitySource
    private lateinit var deletingCity:City

    companion object {
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
        requestResult(view)
        Log.d("SelectCityActivity", "onCreate")
    }

    private fun initCityList() {
        citySource = CitySource((activity as CoordinatorActivity).db.cityDAO())
        if (citySource.allCities.isEmpty()) {
            citySource.addCity(City("Moscow"))
            citySource.addCity(City("Saint Petersburg"))
            citySource.addCity(City("Saratov"))
        }
    }

    override fun onResume() {
        super.onResume()
//        selectCityName(mView)
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

        val cancelButton: Button = view.findViewById(R.id.cancel_button_select_city)
        cancelButton.setOnClickListener {
            closeFragment()
        }
    }

    private fun requestCityName(cityNameResult: String) {
        WeatherPresenter.city = City(cityNameResult)
        val bundle = Bundle()
        bundle.putString(CITY_NAME, cityNameResult)
        (context as AppCompatActivity)
            .supportFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
        (context as StartFragment).runFragments(FragmentsNames.MainFragment, bundle)
    }

    private fun closeFragment() {
        (context as AppCompatActivity)
            .supportFragmentManager
            .popBackStack()
    }
}