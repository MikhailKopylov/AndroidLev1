package com.amk.weatherforall.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Constants.CITY_NAME
import com.amk.weatherforall.core.interfaces.Publisher
import com.amk.weatherforall.core.interfaces.PublisherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import kotlinx.android.synthetic.main.fragment_select_city.*

class SelectCityFragment private constructor() : Fragment() {

    private lateinit var publisher: Publisher

    companion object {
        fun getInstance(): SelectCityFragment {
            return SelectCityFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisher = (context as PublisherGetter).getPublisher()
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
        requestResult(view)
        selectCityName(view = view)
        Log.d("SelectCityActivity", "onCreate")
    }

    private fun requestResult(view: View) {
        val okButton: Button = view.findViewById(R.id.ok_button_select_city)
        okButton.setOnClickListener {
            requestCityName(view)
        }

        val cancelButton: Button = view.findViewById(R.id.cancel_button_select_city)
        cancelButton.setOnClickListener {
            closeFragment()
        }
    }

    private fun selectCityName( view: View) {
        val writeCityNameEditText: EditText = view.findViewById(R.id.writeCityNameEditText)
        writeCityNameEditText.visibility = View.INVISIBLE

        val cityNameBeforeSelect:String = arguments?.getString(CITY_NAME).toString()
        val selectCityRadioGroup: RadioGroup = view.findViewById(R.id.select_city_radioGroup)
        setPresetRadioButton(selectCityRadioGroup, cityNameBeforeSelect)
        selectCityRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
                R.id.city_manually_radioButton -> {
                    writeCityNameEditText.visibility = View.VISIBLE
                }
                else -> {
                    writeCityNameEditText.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setPresetRadioButton(
        selectCityRadioGroup: RadioGroup,
        cityNameBeforeSelect: String
    ) {
        for (radioButton in selectCityRadioGroup.children) {
            if ((radioButton as AppCompatRadioButton).text == cityNameBeforeSelect) {
                radioButton.isChecked = true
                return
            }
        }
        (selectCityRadioGroup.getChildAt(selectCityRadioGroup.childCount - 1) as AppCompatRadioButton)
            .isChecked = true
        writeCityNameEditText.visibility = View.VISIBLE
    }


    private fun requestCityName(view: View) {
        val writeCityNameEditText: EditText = view.findViewById(R.id.writeCityNameEditText)

        val selectCityRadioGroup: RadioGroup = view.findViewById(R.id.select_city_radioGroup)
        val radioButtonId: Int = selectCityRadioGroup.checkedRadioButtonId
        val radioButtonCityName: RadioButton = view.findViewById(radioButtonId)

        when (radioButtonId) {
            R.id.city_manually_radioButton -> {
                requestCityName(writeCityNameEditText.text.toString())
            }
            else -> {
                requestCityName(radioButtonCityName.text.toString())
            }
        }
    }

    private fun requestCityName(cityNameResult: String) {
        publisher.notify(cityNameResult)
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
        fragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("SelectCityFragment", "onCreate")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("SelectCityFragment", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("SelectCityFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SelectCityFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SelectCityFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SelectCityFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("SelectCityFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SelectCityFragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("SelectCityFragment", "onDetach")
    }

}