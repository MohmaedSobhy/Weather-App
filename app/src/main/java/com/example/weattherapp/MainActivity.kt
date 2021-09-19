package com.example.weattherapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.weattherapp.ApI.WeatherInFo
import com.example.weattherapp.ApI.GetAccess
import com.example.weattherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val API="143098d761432cee5bd930e805e02401"
    val CITY="cairo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       val binding:ActivityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
       binding.mainContainer.visibility=View.GONE
       binding.errorText.setText("Some Thing Wrong Check The Internte")
       binding.loader.visibility=View.VISIBLE



       val retrofit=Retrofit.Builder()
          .baseUrl("http://api.openweathermap.org/data/2.5/")
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(GetAccess::class.java)

         val data=retrofit.getWeather(CITY,API);

         data.enqueue(object : Callback<WeatherInFo>{

            override fun onResponse(call: Call<WeatherInFo>, response: Response<WeatherInFo>) {
                if(response.isSuccessful)
                {
                    binding.loader.visibility=View.GONE
                    binding.mainContainer.visibility=View.VISIBLE
                    val information=response.body();

                    binding.address.setText(information?.sys?.country+" "+information?.name)
                    binding.updatedAt.setText("Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(information?.dt!!*1000)))

                    binding.temp.setText(information?.main?.temp.toString()+"°C")
                    binding.tempMin.setText("Min Temp: "+information?.main?.temp_min.toString()+"°C")
                    binding.tempMax.setText("Max Temp: "+information?.main?.temp_max.toString()+"°C")

                    binding.status.setText(information?.weather?.get(0)?.description.toString())
                    binding.sunrise.setText(SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(information?.sys?.sunrise!!*1000)))
                    binding.sunset.setText(SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(information?.sys?.sunset!!*1000)))
                    binding.pressure.setText(information?.main?.pressure.toString())
                    binding.wind.setText(information?.wind?.speed.toString())
                    binding.humidity.setText(information?.main?.humidity.toString())
                }
            }
            override fun onFailure(call: Call<WeatherInFo>, t: Throwable) {
            }
        })


    }

}