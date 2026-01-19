package com.loyltworks.mandelapremium.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class DashBoardViewModel : BaseViewModel() {

    private val _dashboardLiveData = MutableLiveData<DashboardResponse>()
    val dashboardLiveData: LiveData<DashboardResponse> = _dashboardLiveData

    private val _dashboardLiveData2 = MutableLiveData<DashboardCustomerResponse>()
    val dashboardLiveData2: LiveData<DashboardCustomerResponse> = _dashboardLiveData2

    private val _attributeDetails = MutableLiveData<AttributeResponse>()
    val brandLogos: LiveData<AttributeResponse> = _attributeDetails

    fun getDashBoardData(dashboardRequest: DashboardRequest) {
        ///launch the coroutine scope
        scope.launch {
            _dashboardLiveData.postValue(apiRepository?.getDashBoardData(dashboardRequest))
        }
    }

    fun getDashBoardData2(dashboardCustomerRequest: DashboardCustomerRequest) {
        ///launch the coroutine scope
        scope.launch {
            _dashboardLiveData2.postValue(apiRepository?.getDashBoardData2(dashboardCustomerRequest))
        }
    }

    fun getBrandLogos(attributeRequest: AttributeRequest) {
        ///launch the coroutine scope
        scope.launch {
            _attributeDetails.postValue(apiRepository?.getAttributeDetails(attributeRequest))
        }
    }

    /*** Check Update and Maintenance ***/

/*
    var UPDATE_URL = "http://appupdate.arokiait.com/updates/serviceget?pid=com.oneloyalty.goodpack"

    var SUCCESS = true
    var FAILURE = false


    private val _isUpdateAvailable = MutableLiveData<Boolean>()

    fun get_isUpdateAvailable(): LiveData<Boolean>? {
        return _isUpdateAvailable
    }

    var dialog: Dialog? = null

    fun checkIfUpdateAvailabe(context: Context?, currentVersionCode: Int) {
        RegistrationAsyncTask(
            context, UPDATE_URL, SUCCESS,
            object : RegistrationAsyncTask.RegistrationResponse {
                @Throws(
                    ParserConfigurationException::class,
                    IOException::class,
                    SAXException::class,
                    JSONException::class
                )
                override fun onSuccess(status: Boolean, response: String?) {
                    if (status) {
                        val jsonObject = JSONObject(response)
                        if (java.lang.Boolean.parseBoolean(jsonObject.getString("Status"))) {
                            val source = jsonObject.getJSONObject("Result")
                            if (source.getString("version_number").toInt() > currentVersionCode
                                || source.getString("is_maintenance").toInt() == 1
                            ) {
                                if (dialog == null) {
                                    dialog = Dialog(context!!)
                                    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                    dialog!!.setCanceledOnTouchOutside(false)
                                    dialog!!.setCancelable(false)
                                    val window = dialog!!.window
//                                    window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    window!!.setLayout(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT
                                    )
                                }
                                if (source.getString("version_number")
                                        .toInt() > currentVersionCode
                                ) {
                                    dialog!!.setContentView(R.layout.update_alert_dialog)
                                    val textDialog =
                                        dialog!!.findViewById<View>(R.id.textDialog) as TextView
                                    textDialog.text =
                                        context!!.resources.getString(R.string.update_msg)
                                    textDialog.setPadding(15, 15, 15, 15)
                                    textDialog.gravity = Gravity.CENTER
                                    val textOk = dialog!!.findViewById<Button>(R.id.update_app_btn)

                                    // if button is clicked, close the custom dialog
                                    textOk.setOnClickListener {
                                        _isUpdateAvailable.setValue(
                                            SUCCESS
                                        )
                                    }
                                    dialog!!.show()
                                } else if (source.getString("is_maintenance").toInt() == 1) {
                                    dialog!!.setContentView(R.layout.maintenance_dialog)
                                    dialog!!.show()
                                    _isUpdateAvailable.value = FAILURE
                                }
                            } else {
                                _isUpdateAvailable.value = FAILURE
                                if (dialog != null) {
                                    dialog!!.dismiss()
                                    dialog = null
                                }
                            }
                        }
                    }
                }

                override fun onFailure(status: Boolean?, response: String?) {
                    _isUpdateAvailable.setValue(FAILURE)
                }
            }).execute()
    }
*/




}