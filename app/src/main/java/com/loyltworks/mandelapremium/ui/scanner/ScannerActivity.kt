
package com.loyltworks.mandelapremium.ui.scanner

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityScannerBinding
import com.loyltworks.mandelapremium.model.QRCodeSaveRequestLists
import com.loyltworks.mandelapremium.model.ScanRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.LocationAddress
import com.loyltworks.mandelapremium.utils.MediaPlayerPool
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.DialogueCallBack
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.loyltworks.mandelapremium.utils.dialogBox.ScanDialogCallback
import com.loyltworks.mylibrary.qrcodescanner.QrCodeScanner
import com.loyltworks.mylibrary.qrcodescanner.QrCodeScannerListner
import java.text.SimpleDateFormat
import java.util.Date

class ScannerActivity : BaseActivity(), QrCodeScannerListner , Listener {

    lateinit var binding: ActivityScannerBinding

    var latitude: String = ""
    var longitude: String = ""
    var address: String = ""
    var city: String = ""
    var country: String = ""
    var state: String = ""
    var pincode: String = ""

    private lateinit var mediaPlayer: MediaPlayerPool
    private lateinit var scanCodes: String

    private lateinit var viewModel: ScannerViewModel

    private lateinit var easyWayLocation: EasyWayLocation

    override fun callInitialServices() {
        // Get location
        easyWayLocation = EasyWayLocation(this, false, this)
        easyWayLocation.startLocation()

    }

    override fun callObservers() {

        viewModel.scanCodeLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.QRCodeSaveResponseList.isNullOrEmpty()) {
                if (it.QRCodeSaveResponseList!![0].CodeStatus!! >0) {
                    LoadingDialogue.dismissDialog()
                    ScanDialogCallback.showPopUpDialog(
                        this,
                        "Scanned code submitted successfully",
                        scanCodes,
                        object : DialogueCallBack {
                            override fun onScanAgain() {
//                            scanLayout.visibility = View.VISIBLE
                                QrCodeScanner.resumeScan()
                            }

                            override fun onBack() {
                                onBackPressed()
                            }
                        })

                } else {
                    snackBar("Failed to submit scan code", R.color.red)
                    LoadingDialogue.dismissDialog()
                    //scanLayout.visibility = View.VISIBLE
                }
                LoadingDialogue.dismissDialog()
            } else {
                snackBar("Failed to submit scan code", R.color.red)
                LoadingDialogue.dismissDialog()
                //scanLayout.visibility = View.VISIBLE
            }

        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ScannerViewModel::class.java)
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize scanner
        scannerCameraInit()

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }

        binding.productQRCode.setOnClickListener{
            binding.productQRCode.background = ContextCompat.getDrawable(this,R.drawable.tab_selected)
            binding.productQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.black))
            binding.toolbarTitle.setTextColor(ContextCompat.getColor(this,R.color.white))
            binding.raffleQRCode.background = null
            binding.raffleQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.white))
            binding.productQRCodeLayout.visibility = View.VISIBLE
            binding.raffleQRCodeLayout.visibility = View.GONE
        }

        binding.raffleQRCode.setOnClickListener{
            binding.raffleQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.black))
            binding.raffleQRCode.background = ContextCompat.getDrawable(this,R.drawable.tab_selected)
            binding.productQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.black))
            binding.toolbarTitle.setTextColor(ContextCompat.getColor(this,R.color.black))
            binding.productQRCode.background = null
            binding.productQRCodeLayout.visibility = View.GONE
            binding.raffleQRCodeLayout.visibility = View.VISIBLE
        }
    }


    private fun scannerCameraInit() {
        QrCodeScanner.startScanner(this,binding.scannerPreView,this)
    }


    override fun onFailed(s: String) {

    }

    override fun onSuccess(scanCode: String) {
        Log.d(TAG, "onSuccess: $scanCode")
        checkSaveQrCode(scanCode)
    }

    override fun locationOn() {
        Log.d("Locations : ", "Location ON")
    }

    override fun currentLocation(location: Location?) {
        if(location!=null) {
            LoadingDialogue.dismissDialog()
            latitude = location?.latitude.toString()
            longitude = location?.longitude.toString()
            Log.d("Locations : ", "${location?.latitude} ,   ${location?.longitude}")
            LocationAddress.getAddress(this, location?.latitude!!, location.longitude)
            Log.d(
                TAG,
                "currentLocation: ${
                    LocationAddress.getAddress(
                        this,
                        location.latitude,
                        location.longitude
                    )
                }"
            )
            val lstValues: List<String> =
                LocationAddress.getAddress(this, location?.latitude!!, location.longitude)
                    .split(":").map { it -> it.trim() }

            address = lstValues[0]
            country = lstValues[1]
            state = lstValues[3]
            city = lstValues[6]
            pincode = lstValues[4]

            Log.d(TAG, "Address: ${lstValues[0]}")
            Log.d(TAG, "country: ${lstValues[1]}")
            Log.d(TAG, "state: ${lstValues[3]}")
            Log.d(TAG, "city: ${lstValues[6]}")
            Log.d(TAG, "pincode: ${lstValues[4]}")

            lstValues.forEach { it ->
                Log.i("Values", "value=$it")
                //Do Something
            }
        }else{
            LoadingDialogue.showDialog(this)
        }

    }

    override fun locationCancelled() {
        Log.d("Locations : ", "Location Cancelled")
    }


    @SuppressLint("SimpleDateFormat")
    private fun checkSaveQrCode(scanCode: String) {
        scanCodes = scanCode
//        if (scanCode.length != 13  && qrCheckDigit(scanCode.toCharArray())) {
//            // display snack bar on In-Valid/failed scan
//            (activity as MainActivity).snackBar(
//                getString(R.string.flashcode_scan_not_valid), R.color.red
//            )
//
//            try {//play music
//                mediaPlayer.playSound(R.raw.failure)
//            } catch (e: Exception) {}
//
//        } else{
//        scanLayout.visibility = View.GONE
//        QrCodeScanner.pauseScan()
        QrCodeScanner.logPrint(true)
        LoadingDialogue.showDialog(this)
//        viewModel.setValidateScratchCode(
//            ValidateScratchCodeRequest(
//                ActionType = "5",
//                ScratchCode = scanCodes,
//                LoyaltyID = PreferenceHelper.getLoginDetails(
//                    this
//                )!!.UserList!![0].UserName
//            )
//        )
        Log.d(TAG, "checkSaveQrCode: " + scanCode)

        if(latitude.isNotEmpty() && longitude.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty() && state.isNotEmpty() && pincode.isNotEmpty()) {
            val scanCodeList: ArrayList<QRCodeSaveRequestLists> =
                ArrayList()
//            val customerOtherInfo: ArrayList<CustomerOtherInFoRequests> = ArrayList<CustomerOtherInFoRequests>()
            val qrCodeSaveRequestLists = QRCodeSaveRequestLists();
            qrCodeSaveRequestLists.QRCode = scanCodes
            scanCodeList.add(qrCodeSaveRequestLists)
            viewModel.submitScanCode(
                ScanRequest(
                    Address = address,
//                    CustomerOtherInFoRequest = customerOtherInfo,
                    QRCodeSaveRequestList = scanCodeList,
                    City = city,
                    LoyaltyID = PreferenceHelper.getLoginDetails(this)!!.UserList!![0].UserName,
                    Country = country,
                    SourceType = "1",
                    Latitude = latitude,
                    Longitude = longitude,
                    State = state,
                    PinCode = pincode,
                    AccessedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
                )
            )
        }

//        }

    }


    override fun onResume() {
        super.onResume()
//        easyWayLocation.startLocation()
    }

    override fun onPause() {
        super.onPause()
        easyWayLocation.endUpdates()
    }

}