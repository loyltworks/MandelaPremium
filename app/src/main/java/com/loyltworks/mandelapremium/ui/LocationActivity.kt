package com.loyltworks.mandelapremium.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityLocationBinding
import com.loyltworks.mandelapremium.model.GenderSpinner
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.profile.adapter.GenderAdapter

class LocationActivity: BaseActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback ,
    AdapterView.OnItemSelectedListener {

    lateinit var binding: ActivityLocationBinding

    private val PERTH = LatLng(0.3131384, 32.498325)
    private val SYDNEY = LatLng(0.3183364,32.5099587)
    private val BRISBANE = LatLng(0.3367545,32.538498)

    private var markerPerth: Marker? = null
    private var markerSydney: Marker? = null
    private var markerBrisbane: Marker? = null
    private var mSelectedGender: GenderSpinner? = null

    var genderList: ArrayList<GenderSpinner> = ArrayList()

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun callInitialServices() {

    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        binding.genderSpinner.onItemSelectedListener = this

        val defaultstatus = GenderSpinner()
        defaultstatus.setId(-1)
        defaultstatus.setName("Select Brand")

        val genderSpinner1 = GenderSpinner()
        genderSpinner1.setId(1)
        genderSpinner1.setName("Cafe Javas")

        val genderSpinner2 = GenderSpinner()
        genderSpinner2.setId(2)
        genderSpinner2.setName("City Tyres")

        val genderSpinner3 = GenderSpinner()
        genderSpinner3.setId(3)
        genderSpinner3.setName("On The Go")

        val genderSpinner4 = GenderSpinner()
        genderSpinner4.setId(4)
        genderSpinner4.setName("The Food Hub")

        val genderSpinner5 = GenderSpinner()
        genderSpinner5.setId(5)
        genderSpinner5.setName("City Retread")

        val genderSpinner6 = GenderSpinner()
        genderSpinner6.setId(6)
        genderSpinner6.setName("ManDela Millers")

        val genderSpinner7 = GenderSpinner()
        genderSpinner7.setId(7)
        genderSpinner7.setName("ManDela Auto Spares")

        genderList.add(defaultstatus)
        genderList.add(genderSpinner1)
        genderList.add(genderSpinner2)
        genderList.add(genderSpinner3)
        genderList.add(genderSpinner4)
        genderList.add(genderSpinner5)
        genderList.add(genderSpinner6)
        genderList.add(genderSpinner7)

        binding.genderSpinner.setAdapter(
            GenderAdapter(
                this,
                android.R.layout.simple_spinner_item,
                genderList
            )
        )

    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when(parent!!.id){
            R.id.genderSpinner ->{
                mSelectedGender = parent.getItemAtPosition(position) as GenderSpinner
                if(mSelectedGender!!.id == 1){
                    binding.mapLayout.visibility = View.VISIBLE
                }else if(mSelectedGender!!.id > 0 ){
                    binding.mapLayout.visibility = View.GONE
                    Toast.makeText(this, "Coming Soon.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onMapReady(map: GoogleMap) {
        // Add some markers to the map and assign tags.
        markerPerth = map.addMarker(
            MarkerOptions()
                .position(PERTH)
                .title("Cafe Javas - Cafe \n Kampala , Uganda")
        )
        markerPerth?.tag = 0

        markerSydney = map.addMarker(
            MarkerOptions()
                .position(SYDNEY)
                .title("Cafe Javas - Restaurant \n Kampala , Uganda")
        )
        markerSydney?.tag = 0

        markerBrisbane = map.addMarker(
            MarkerOptions()
                .position(BRISBANE)
                .title("Cafe Javas - Restaurant \n Kampala , Uganda")
        )
        markerBrisbane?.tag = 0

        // Move the camera to the first location and set the zoom level
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(PERTH, 15f)) // Zoom level: 15f

        // Optional: Enable zoom controls
        map.uiSettings.isZoomControlsEnabled = true

        // Set a listener for marker clicks
        map.setOnMarkerClickListener(this)
    }


    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {

        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount

        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
}