package com.amk.weatherforall.ui.fragments

import androidx.fragment.app.FragmentActivity
import com.amk.weatherforall.R
import com.amk.weatherforall.ui.activities.CoordinatorActivity

fun runFragments(app: FragmentActivity, fragmentName: FragmentsNames) {
    if(app is CoordinatorActivity) {
        app.idFragmentVisible = fragmentName.idFragment
    }
    app.supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, fragmentName.fragment)
//        .addToBackStack(null)
        .commit()
}