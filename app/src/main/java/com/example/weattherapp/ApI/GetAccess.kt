package com.example.weattherapp.ApI

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAccess {
    @GET("weather")
    fun getWeather(@Query("q") city: String?, @Query("appid") api: String?): Call<WeatherInFo>

}