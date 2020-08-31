package com.amk.weatherforall.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.amk.weatherforall.*
import com.amk.weatherforall.core.PublisherImpl
import com.amk.weatherforall.core.interfaces.Publisher
import com.amk.weatherforall.core.interfaces.PublisherGetter
import com.amk.weatherforall.core.interfaces.StartFragment
import com.amk.weatherforall.fragments.FragmentsNames

class MainActivity : AppCompatActivity(), PublisherGetter, StartFragment {

    private val publisher: Publisher = PublisherImpl()


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        runFragments(FragmentsNames.MainFragment,Bundle())

        Log.d("MainActivity", "onCreate")
    }

    override fun runFragments(fragmentName: FragmentsNames, arguments:Bundle) {

        val fragment: Fragment = fragmentName.fragment
        fragment.arguments = arguments

//        if (fragment is Observable) {
//            publisher.subscribe(fragment)
//        }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentName.fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun getPublisher(): Publisher {
        return publisher
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onStart() {
        super.onStart()

        Log.d("MainActivity", "onStart")
    }




}