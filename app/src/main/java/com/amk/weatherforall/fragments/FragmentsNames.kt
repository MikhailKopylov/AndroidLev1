package com.amk.weatherforall.fragments

import androidx.fragment.app.Fragment

enum class FragmentsNames(val fragment: Fragment) {

    MainFragment(com.amk.weatherforall.fragments.MainFragment.getInstance()),
    SelectCityFragment(com.amk.weatherforall.fragments.SelectCityFragment.getInstance()),
    SettingsFragment(com.amk.weatherforall.fragments.SettingsFragment.getInstance())
}