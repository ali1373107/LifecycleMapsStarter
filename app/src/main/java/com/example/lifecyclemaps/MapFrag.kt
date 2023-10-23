package com.example.lifecyclemaps

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapFrag: Fragment(R.layout.mapfrag) {
    val viewModel : LocationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance()
            .load(activity, PreferenceManager.getDefaultSharedPreferences(activity));

        val map1 : MapView = view.findViewById(R.id.map1)
        map1.controller?.setZoom(14.0)
        map1.controller?.setCenter(GeoPoint(viewModel.latLon.lat, viewModel.latLon.lon))

        viewModel.LatLonLiveData.observe(viewLifecycleOwner, {
            map1.controller?.setCenter(GeoPoint(it.lat, it.lon))
        })
    }

}
