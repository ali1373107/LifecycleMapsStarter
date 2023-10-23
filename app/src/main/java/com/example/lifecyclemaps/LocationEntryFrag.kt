package com.example.lifecyclemaps

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class LocationEntryFrag : Fragment(R.layout.locationentryfrag) {
    val viewModel : LocationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState:Bundle?){
        val etPlace = view.findViewById<EditText>(R.id.etPlace)
        view.findViewById<Button>(R.id.btnGo).setOnClickListener{
            val placeName = etPlace.text.toString()

            viewModel.searchPlace(placeName)
        }
    }
    // Implement onViewCreated() to:
    // - listen for button click events
    // - read the location the user entered
    // - send the location to the method in the ViewModel which calls the web API to
    //   find its lat/lon and updates the LiveData appropriately
}