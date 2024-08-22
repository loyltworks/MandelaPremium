package com.loyltworks.mandelapremium.ui.SplashScreen

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import com.loyltworks.mandelapremium.utils.RegistrationAsyncTask
import org.json.JSONException
import org.json.JSONObject
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.ParserConfigurationException

class SplashScreenViewModel : BaseViewModel() {


    var UPDATE_URL = "http://appupdate.arokiait.com/updates/serviceget?pid=com.loyltworks.mandelapremium"

    var SUCCESS = true
    var FAILURE = false


    private val _isUpdateAvailable = MutableLiveData<Boolean>()

    fun get_isUpdateAvailable(): LiveData<Boolean>? {
        return _isUpdateAvailable
    }

    var dialog: Dialog? = null

    fun checkIfUpdateAvailabe(context: Context?, currentVersionCode: Int) {
        RegistrationAsyncTask(context, UPDATE_URL, SUCCESS,
            object : RegistrationAsyncTask.RegistrationResponse {
                @SuppressLint("SetTextI18n")
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
                                    val window: Window? = dialog!!.window
                                   /* window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/
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
                                    textDialog.setText(R.string.update_msg)
                                    textDialog.setPadding(15, 15, 15, 15)
                                    textDialog.gravity = Gravity.CENTER
                                    val textOk: Button =
                                        dialog!!.findViewById<Button>(R.id.update_app_btn)

                                    // if button is clicked, close the custom dialog
                                    textOk.setOnClickListener {
                                        _isUpdateAvailable.setValue(
                                            SUCCESS
                                        )
                                    }
                                    dialog!!.show()
                                } else if (source.getString("is_maintenance").toInt() == 1) {
//                                dialog.setContentView(R.layout.maintenance_dialog);
//                                dialog.show();
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
                    _isUpdateAvailable.value = FAILURE
                }
            }).execute()
    }

}