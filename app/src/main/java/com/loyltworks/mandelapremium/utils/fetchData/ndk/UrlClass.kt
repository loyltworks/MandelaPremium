package com.loyltworks.mandelapremium.utils.fetchData.ndk

object UrlClass {

    fun baseUrl() = DecryptClass.decrypt(NativeConfig.getBaseUrl())

    fun domain() = DecryptClass.decrypt(NativeConfig.getDomain())

    fun promoImageBase() = DecryptClass.decrypt(NativeConfig.getPromoImageBase())

    fun catalogueImageBase() = DecryptClass.decrypt(NativeConfig.getCatalogueImageBase())

    fun shaKey() = DecryptClass.decrypt(NativeConfig.getShaKey())

    fun updateUrl() = DecryptClass.decrypt(NativeConfig.getUpdateUrl())

    fun merchentName() = DecryptClass.decrypt(NativeConfig.getMerchentName())

    fun checkLiveDemo() = DecryptClass.decrypt(NativeConfig.getCheckLiveDemo())

}
