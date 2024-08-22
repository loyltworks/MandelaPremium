package com.loyltworks.mandelapremium.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.ScanRequest
import com.loyltworks.mandelapremium.model.ScanResponse
import com.loyltworks.mandelapremium.model.ValidateScratchCodeRequest
import com.loyltworks.mandelapremium.model.ValidateScratchCodeResponse
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class ScannerViewModel : BaseViewModel() {

    /*ScanCodeLiveData */
    private val _scanCodeLiveData = MutableLiveData<ScanResponse>()
    val scanCodeLiveData: LiveData<ScanResponse> = _scanCodeLiveData
    fun submitScanCode(scanCodeRequest: ScanRequest) {
        scope.launch {
            //get latest news from news repo
            val scanCodeDetail = apiRepository.saveScanCodeData(scanCodeRequest)
            //post the value inside live data
            _scanCodeLiveData.postValue(scanCodeDetail)
        }
    }


    private val _validateScratchCodeResponse = MutableLiveData<ValidateScratchCodeResponse>()
    val validateScratchCodeResponse: LiveData<ValidateScratchCodeResponse> = _validateScratchCodeResponse

    fun setValidateScratchCode(validateScratchCodeRequest: ValidateScratchCodeRequest) {
        scope.launch {
            //get latest news from news repo
            val validateScratchCode = apiRepository.validateScratchCodeRequest(validateScratchCodeRequest)
            //post the value inside live data
            _validateScratchCodeResponse.postValue(validateScratchCode)
        }
    }

}