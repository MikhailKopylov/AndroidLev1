package com.amk.weatherforall.mvp.interfaces

import androidx.room.Insert
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.City.DateLastUseCity

@Dao
interface CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City):Long

    @Update
    fun updateCity(city:City)

    @Delete
    fun deleteCity(city: City)

    @Query("""SELECT idDB, idFromNet, cityName, lon, lat, countryName, population, sunrise, sunset, timezone, date_of_last_use  FROM city
        INNER JOIN dateLastUseCity ON city.idDB = dateLastUseCity.city_id ORDER BY  dateLastUseCity.date_of_last_use DESC, cityName""")
    fun getAllCitiesOrderByLastUse():List<City>

//    @Query("SELECT * FROM city WHERE city.idDB = :id")
//    fun getCityById(id:Long)

//    @Query("""SELECT cityName FROM city
//         INNER JOIN dateLastUseCity ON city.idDB = city_id
//    """)
//    fun getAllCitiesOrderedByLastUse()

    @Query("SELECT COUNT() FROM city")
    fun getCountCities():Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastCity(dateLastUseCity: DateLastUseCity)

    @Query("UPDATE DateLastUseCity SET date_of_last_use = :newDate WHERE city_id_from_net = :city_id_from_net ")
    fun updateLastCity(city_id_from_net:Int, newDate:Long)

//    @Query("SELECT id, city_id,  city_id_from_net, date_of_last_use FROM  dateLastUseCity WHERE city_id_from_net = :cityIdFromNet")
//    fun getDateLastCity(cityIdFromNet: Long): DateLastUseCity

//    @Query("SELECT * FROM dateLastUseCity")
//    fun getCitiesLastUse(): List<DateLastUseCity>

}