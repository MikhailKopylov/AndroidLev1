package com.amk.weatherforall.mvp.model.entity.City

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    indices = [Index(value = ["city_id", "city_id_from_net"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = arrayOf("idDB"),
        childColumns = arrayOf("city_id"), onDelete = CASCADE
    )]
)
class DateLastUseCity(

    @ColumnInfo(name = "city_id")
    val idCity: Long,

    @ColumnInfo(name = "city_id_from_net")
    val idCityFromNet: Int,

    @ColumnInfo(name = "date_of_last_use")
    val positionOfLastUse: Long,

    @PrimaryKey(autoGenerate = true)
    var id: Long
) {
    constructor(idCity: Long, idCityFromNet: Int, positionOfLastUse: Long) : this(
        idCity,
        idCityFromNet,
        positionOfLastUse,
        0
    )
}