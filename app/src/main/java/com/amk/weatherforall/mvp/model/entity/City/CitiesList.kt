package com.amk.weatherforall.mvp.model.entity.City

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File


object CitiesList {

    val citiesMap: HashMap<String, City> = HashMap()

    init {
        val gson = Gson()
        val bufferedReader: BufferedReader = File ("city.list.json") .bufferedReader ()
        val inputString = bufferedReader.use { it.readText() }
        val citiesList: List<City> = gson.fromJson(inputString, Array<City>::class.java).toList()
        citiesList.forEach { City -> citiesMap[City.name] = City }


//        //Initialize the String Builder
//        val stringBuilder = StringBuilder("Post Details\n---------------------")
//        stringBuilder?.append("\nPost Heading: " + post.postHeading)
//        stringBuilder?.append("\nPost URL: " + post.postUrl)
//        stringBuilder?.append("\nPost Author: " + post.postAuthor)
//        stringBuilder?.append("\nTags:")
//        //get the all Tags
//
//        post.postTag?.forEach { tag -> stringBuilder?.append(tag + ",") }
//        //Display the all Json object in text View
//        textView?.setText(stringBuilder.toString())

    }
}