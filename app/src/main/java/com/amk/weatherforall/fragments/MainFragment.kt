package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants
import com.amk.weatherforall.core.Constants.CITY_NAME
import com.amk.weatherforall.core.interfaces.Observable
import com.amk.weatherforall.core.interfaces.StartFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainFragment private constructor() : Fragment(), Observable {
    private lateinit var cityTextView: TextView
    private var showTemperatureInC: Boolean = true
    private val TEMPERATURE_C = 15

    private var isWindVisible: Boolean = true
    private var isPressureVisible: Boolean = true

    private lateinit var fragmentView: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        cityTextView = view.findViewById(R.id.location_text_view)

        val additionalInformationButton: Button =
            view.findViewById(R.id.additional_information_button)
        additionalInformationButton.setOnClickListener {
            val uri: Uri = Uri.parse(resources.getString(R.string.defaultUrl))
            val browser = Intent(Intent.ACTION_VIEW, uri)
            startActivity(browser)
        }
        clickSettings(view)

        cityTextView.setOnClickListener {
            val bundle= Bundle()
            bundle.putString(CITY_NAME, cityTextView.text.toString())
            arguments = bundle
            (context as StartFragment).runFragments(FragmentsNames.SelectCityFragment, bundle)
        }

        update(view)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when (requestCode) {
//            Constants.REQUEST_CODE_SELECT_CITY -> {
//                val cityName: String = data?.getStringExtra(Constants.CITY_NAME_SELECT)
//                    ?: resources.getString(R.string.Default_city_name)
//                cityTextView.text = cityName
////                update()
//            }
//            Constants.REQUEST_CODE_SETTING -> {
//                if (data != null) {
//                    showTemperatureInC =
//                        data.getBooleanExtra(Constants.SETTING_SHOW_MODE_TEMPERATURE, true)
//                    isPressureVisible = data.getBooleanExtra(Constants.SETTING_SHOW_PRESSURE, true)
//                    isWindVisible = data.getBooleanExtra(Constants.SETTING_SHOW_WIND, true)
//                }
//
//            }
//            else -> {
//                super.onActivityResult(requestCode, resultCode, data)
//                return
//            }
//        }
//    }

    private fun clickSettings(view: View) {
        val settingsButton: ImageButton = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Constants.SETTING_SHOW_MODE_TEMPERATURE, showTemperatureInC)
            bundle.putBoolean(Constants.SETTING_SHOW_PRESSURE, isPressureVisible)
            bundle.putBoolean(Constants.SETTING_SHOW_WIND, isWindVisible)
            arguments = bundle
//            startActivityForResult(intent, Constants.REQUEST_CODE_SETTING)
        }
    }


    private fun temperatureMode(showInC: Boolean): String {
        return if (showInC) {
            "$TEMPERATURE_C C"
        } else {
            "${TEMPERATURE_C.convertToF()} F"
        }

    }

    private fun update(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
        val timeTextView: TextView = view.findViewById(R.id.time_text_view)
        val updateButton: ImageButton = view.findViewById(R.id.update_button)
        dateTextView.text = dateNow()
        timeTextView.text = timeNow()
        updateButton.setOnClickListener {
            dateTextView.text = dateNow()
            timeTextView.text = timeNow()

        }

        val temperatureTextView: TextView = view.findViewById(R.id.temperature_text_view)
        temperatureTextView.text = temperatureMode(showTemperatureInC)

        val pressureTextView: TextView = view.findViewById(R.id.pressure_textView)
        if (isPressureVisible) {
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }

        val windTextView: TextView = view.findViewById(R.id.wind_textView)
        if (isWindVisible) {
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }

        if(arguments?.getString(CITY_NAME) != null){
            cityTextView.text = arguments?.getString(CITY_NAME)?:"ERROR"
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeNow(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            if (current.minute > 9) {
                "${current.hour}:${current.minute}"
            } else {
                "${current.hour}:0${current.minute}"
            }
        } else {
            val formatterTime = SimpleDateFormat("hh:mma")
            formatterTime.format(Date())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateNow(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()

            if (current.monthValue > 9) {
                "${current.dayOfMonth}.${current.monthValue}.${current.year}"
            } else {
                "${current.dayOfMonth}.0${current.monthValue}.${current.year}"
            }
        } else {
            val formatterDate = SimpleDateFormat("dd.mm.yyyy")
            formatterDate.format(Date())
        }
    }

    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()

    override fun updateCityName(text: String) {
        cityTextView.text = text
        val bundle = Bundle()
        bundle.putString(CITY_NAME, text)
        arguments = bundle
        update(fragmentView)
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString(CITY_NAME, cityTextView.text.toString())
//        Log.d("MainFragment", "onSaveInstanceState")
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        if(savedInstanceState != null){
//            cityTextView.text = savedInstanceState.getString(CITY_NAME)
//        }
//        Log.d("MainFragment", "onViewStateRestored")
//        super.onViewStateRestored(savedInstanceState)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        Log.d("MainFragment", "onAttach")
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        Log.d("MainFragment", "onCreate")
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        Log.d("MainFragment", "onActivityCreated")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d("MainFragment", "onStart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("MainFragment", "onResume")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("MainFragment", "onPause")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("MainFragment", "onStop")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.d("MainFragment", "onDestroyView")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("MainFragment", "onDestroy")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.d("MainFragment", "onDetach")
//    }
}