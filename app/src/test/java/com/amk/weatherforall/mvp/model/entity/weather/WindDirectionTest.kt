package com.amk.weatherforall.mvp.model.entity.weather

import org.junit.Assert.*
import org.junit.Test

class WindDirectionKtTest{

    @Test
    fun resultNorth1(){
        for(degree in 338..360){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.North)
        }
    }

    @Test
    fun resultNorth(){
        for(degree in 0..22){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.North)
        }
    }

    @Test
    fun resultEast(){
        for(degree in 68..112){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.East)
        }
    }

    @Test
    fun resultSouthEast(){
        for(degree in 113..157){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.SouthEast)
        }
    }

    @Test
    fun resultSouth(){
        for(degree in 158..202){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.South)
        }
    }

    @Test
    fun resultSouthWest(){
        for(degree in 203..247){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.SouthWest)
        }
    }

    @Test
    fun resultWest(){
        for(degree in 248..292){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.West)
        }
    }

    @Test
    fun resultNorthWest(){
        for(degree in 293..337){
            assertEquals( convertDegreesToDirectionWind(degree), WindDirection.NorthWest)
        }
    }

}