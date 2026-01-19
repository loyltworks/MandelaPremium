package com.loyltworks.mandelapremium.utils.fetchData.ndk

object NativeConfig {

    init {
        System.loadLibrary("my_native_library")
    }

    external fun getBaseUrl(): String
    external fun getDomain(): String
    external fun getPromoImageBase(): String
    external fun getCatalogueImageBase(): String
    external fun getShaKey(): String
    external fun getUpdateUrl(): String
    external fun getMerchentName(): String
    external fun getCheckLiveDemo(): String
}
