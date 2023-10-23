package com.example.lifecyclemaps


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONArray

// You'll find these imports useful for the network communication and JSON parsing
import org.json.JSONObject
import java.net.URL

data class LatLon(var lat: Double=0.0, var lon: Double=0.0)

class LocationViewModel : ViewModel(){
    // Create a latLon property (of type LatLon) and corresponding LiveData, as last week
    val LatLonLiveData = MutableLiveData<LatLon>()
    val searchResult: LiveData<LatLon>
        get() =LatLonLiveData
    var  latLon: LatLon = LatLon()
        set(value){
            field = value
            LatLonLiveData.value = value
        }
    // Write a method to search for a given place by name
    fun searchPlace(place_Name:String){
        viewModelScope.launch{
            try{
                val apiUrl = "https://opentrailview.org/nomproxy.php?q=$place_Name"
                val jsonResponse = URL(apiUrl).readText()
                val jsonArray = JSONArray(jsonResponse)
                if (jsonArray.length()>0){
                    val first_Obj = jsonArray.getJSONObject(0)
                    val lat = first_Obj.getDouble("lat")
                    val lon = first_Obj.getDouble("lon")
                    val latLon = LatLon(lat, lon)
                    LatLonLiveData.postValue(latLon)
                }else{
                    LatLonLiveData.postValue(LatLon())
                }
            } catch (e:java.lang.Exception){
                Log.e(TAG, "An error occurred: ${e.message}")
                LatLonLiveData.postValue(LatLon()) //Set it to a default value

            }
        }

    }
    // This should use a coroutine and:
    // - call https://hikar.org/webapp/map/search?q=<the name the user entered>
    // - parse the JSON returned. It's one object containing "lat" and "lon" fields
    // - set the latLon property to be a LatLon object containing the data from the JSON
}