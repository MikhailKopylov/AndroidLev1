package com.amk.weatherforall.fragments

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.amk.weatherforall.R
import com.amk.weatherforall.viewModels.BottomNavigationViewModel

fun runFragments(app: FragmentActivity, fragmentName: FragmentsNames) {
    app.supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, fragmentName.fragment)
//        .addToBackStack(null)
        .commit()
}