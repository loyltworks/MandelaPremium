package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDetailsRequest(
    val ActionType: Int? = null,
    val IsActive: Int? = null
)


@JsonClass(generateAdapter = true)
data class CountryDetailResponse(
    var lstCountryDetails: List<LstCountryDetail>? = null
)

@JsonClass(generateAdapter = true)
data class LstCountryDetail(
    val CountryCode: String? = null,
    val CountryFlag: String? = null,
    val CountryId: Int? = null,
    val CountryName: String? = null,
    val CountryType: Any? = null,
    val IsActive: Boolean? = null,
    val Limitation: Int? = null,
    val MobilePrefix: String? = null,
    val Row: Int? = null
)


@JsonClass(generateAdapter = true)
data class CommonSpinner(
    var name: String? = null,
    val id: Int? = null,
    val type: String? = null
)
