package com.amk.weatherforall.ui.fragments.selectCityFragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.ui.activities.CoordinatorActivity
import com.amk.weatherforall.ui.activities.MapsActivity
import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.City.Coord
import com.amk.weatherforall.mvp.presenter.WeatherPresenter
import com.amk.weatherforall.mvp.model.database.CitySource
import com.amk.weatherforall.ui.fragments.FragmentsNames
import com.amk.weatherforall.ui.fragments.dialogs.DeleteCityDialog
import com.amk.weatherforall.ui.fragments.dialogs.OnDialogListener
import com.amk.weatherforall.ui.fragments.runFragments
import com.amk.weatherforall.viewModels.BottomNavigationViewModel
import com.amk.weatherforall.viewModels.SelectCityViewModel

class SelectCityFragment : Fragment() {



    private lateinit var mView: View

    private lateinit var recyclerView: RecyclerView
    lateinit var cityListAdapter: CityListAdapter

    private lateinit var citySource: CitySource
    private lateinit var deletingCity: City

    private lateinit var modelCity: SelectCityViewModel

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

        modelCity =
            ViewModelProviders.of(activity ?: return).get(SelectCityViewModel::class.java)
        initRecyclerView(view)

        requestResult(view)
        val selectCoordButton: Button = view.findViewById(R.id.show_on_map_button)
        selectCoordButton.setOnClickListener {
//            activity?.supportFragmentManager
//                ?.popBackStack()

            startActivityForResult(
                Intent(context, MapsActivity::class.java),
                REQUEST_CODE_MAPS_COORD
            )
        }
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.city_list_view)
        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        cityListAdapter = CityListAdapter(citySource.allCities)
        recyclerView.adapter = cityListAdapter

        recyclerView.smoothScrollToPosition(0)

        cityListAdapter.setOnItemClickListener(object :
            CityListAdapter.OnCityItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
                requestCity(City(citySource.allCities[position].name, Coord(Double.NaN, Double.NaN)))
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

    }


    private fun requestResult(view: View) {
        val okButton: Button = view.findViewById(R.id.ok_button_select_city)
        val newCityEditText: EditText = view.findViewById(R.id.writeCityNameEditText)
        okButton.setOnClickListener {
            val newCityName = newCityEditText.text.toString()
            newCityEditText.setText("")
//            citySource.addCity(City(newCityName))
            requestCity(City(newCityName, Coord(Double.NaN, Double.NaN)))
        }
    }

    private fun requestCity(cityNameResult: City) {

        modelCity.cityName(cityNameResult)

        runFragments(activity ?: return, FragmentsNames.MainFragment)
        val bottomNavigationViewModel: BottomNavigationViewModel = ViewModelProviders.of(activity?:return).get(
            BottomNavigationViewModel::class.java)
        bottomNavigationViewModel.selectItemBottom(R.id.navigation_home)

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
                requestCity(City(WeatherPresenter.UNKNOWN_CITY_NAME, Coord(latitude, longitude)))
            }
        }
    }

}