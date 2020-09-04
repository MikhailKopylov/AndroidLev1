package com.amk.weatherforall.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.weatherforall.R
import com.amk.weatherforall.core.Weather
import com.amk.weatherforall.core.interfaces.PublisherWeather
import com.amk.weatherforall.core.interfaces.PublisherWeatherGetter

class NextWeathersFragment(private val nextWeatherList:List<Weather>): Fragment() {

    lateinit var publisherWeather: PublisherWeather


    companion object{
        fun getInstance(weathers:List<Weather>):NextWeathersFragment{
            return NextWeathersFragment(weathers)
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
        return inflater.inflate(R.layout.fragment_next_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView:RecyclerView = view.findViewById(R.id.nextWeather_fragment)
        val linearLayoutManager= LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        val nextWeatherAdapter = NextWeatherAdapter(nextWeatherList.toMutableList())
        recyclerView.adapter = nextWeatherAdapter

        nextWeatherAdapter.setOnItemClickListener(object :NextWeatherAdapter.onWeatherItemClickListener{
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
                publisherWeather.notify(nextWeatherList[position ])
            }
        })

    }
}