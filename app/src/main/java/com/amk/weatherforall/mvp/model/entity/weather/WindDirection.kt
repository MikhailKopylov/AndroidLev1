package com.amk.weatherforall.mvp.model.entity.weather

enum class WindDirection {
    North,
    NorthEast,
    East,
    SouthEast,
    South,
    SouthWest,
    West,
    NorthWest,
}


fun convertDegreesToDirectionWind(degree: Int): WindDirection =
    when (degree) {
        in 338..360 -> WindDirection.North
        in 0..22 -> WindDirection.North
        in 23..67 -> WindDirection.NorthEast
        in 68..112 -> WindDirection.East
        in 113..157 -> WindDirection.SouthEast
        in 158..202 -> WindDirection.South
        in 203..247 -> WindDirection.SouthWest
        in 248..292 -> WindDirection.West
        in 293..337 -> WindDirection.NorthWest
        else -> throw IllegalArgumentException("Incorrect wind direction value")
    }
