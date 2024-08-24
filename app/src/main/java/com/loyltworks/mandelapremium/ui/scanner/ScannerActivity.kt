
package com.loyltworks.mandelapremium.ui.scanner

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.QRCodeSaveRequestLists
import com.loyltworks.mandelapremium.model.ScanRequest
import com.loyltworks.mandelapremium.model.ValidateScratchCodeRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.LocationAddress
import com.loyltworks.mandelapremium.utils.MediaPlayerPool
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.DialogueCallBack
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.loyltworks.mandelapremium.utils.dialogBox.ScanDialogCallback
import com.vmb.scanner.Scanner
import com.vmb.scanner.Scanner.Already_Code_Scanned
import com.vmb.scanner.Scanner.resumeScan
import com.vmb.scanner.ScannerListener
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.scan_success_below_popup_layout.*
import java.text.SimpleDateFormat
import java.util.*
@RequiresApi(Build.VERSION_CODES.M)
class ScannerActivity : BaseActivity(), ScannerListener , Listener {

    public var latitude: String = ""
    public var longitude: String = ""
    public var address: String = ""
    public var city: String = ""
    public var country: String = ""
    public var state: String = ""
    public var pincode: String = ""

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
                                Scanner.resumeScan()
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
        setContentView(R.layout.activity_scanner)

        // initialize scanner
        scannerCameraInit()

        back_arrow.setOnClickListener {
            onBackPressed()
        }

        productQRCode.setOnClickListener{
            productQRCode.background = ContextCompat.getDrawable(this,R.drawable.tab_selected)
            productQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.black))
            toolbarTitle.setTextColor(ContextCompat.getColor(this,R.color.white))
            raffleQRCode.background = null
            raffleQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.white))
            productQRCodeLayout.visibility = View.VISIBLE
            raffleQRCodeLayout.visibility = View.GONE
        }

        raffleQRCode.setOnClickListener{
            raffleQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.black))
            raffleQRCode.background = ContextCompat.getDrawable(this,R.drawable.tab_selected)
            productQRCodeText.setTextColor(ContextCompat.getColor(this,R.color.black))
            toolbarTitle.setTextColor(ContextCompat.getColor(this,R.color.black))
            productQRCode.background = null
            productQRCodeLayout.visibility = View.GONE
            raffleQRCodeLayout.visibility = View.VISIBLE
        }



        Scanner.cameraSelect(Scanner.BackCamera)

    }


    private fun scannerCameraInit() {
       /* Scanner.startScanner(this, scannerPreView, this)
            .checkCodeExists(false)
            .setResolution(Scanner.Low_Resolution)
            .logPrint(true)
            .muteBeepSound(true)
            .scanDelayTime(1000)*/


        Scanner.startScanner(this, scannerPreView, this)
            .checkCodeExists(false)
            .setResolution(Scanner.Low_Resolution)
            .logPrint(true)
//            .muteBeepSound(true)
            .scanDelayTime(2000)
    }


    override fun onFailed(s: String) {
        Log.d("codeScaneerFailed", ": $s")
        if (!s.equals(Already_Code_Scanned, ignoreCase = true)) {
            snackBar("Code is already scanned", R.color.red)
            resumeScan()
        } else {
            snackBar(s, R.color.red)
            resumeScan()
        }

    }

    override fun onSuccess(scanCode: String) {
        Log.d(TAG, "onSuccess: " + scanCode)
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
//        Scanner.pauseScan()
        Scanner.logPrint(true)
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
                ArrayList<QRCodeSaveRequestLists>()
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