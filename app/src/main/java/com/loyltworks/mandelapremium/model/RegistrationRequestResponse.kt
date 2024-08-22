package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    val ActionType: String? = null,
    val DeviceId: String? = null,
    val CustomerId: String? = null
)

// ####################### Response  ########################

@JsonClass(generateAdapter = true)
data class RegistrationResponse(
    val GetCustomerDetailsMobileAppResult: GetCustomerDetailsMobileAppResult
)

@JsonClass(generateAdapter = true)
data class GetCustomerDetailsMobileAppResult(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstCustomerIdentityInfo: List<LstCustomerIdentityInfo>? = null,
    val lstCustomerJson: List<LstCustomerJson>? = null,
    val lstCustomerOfficalInfoJson: List<LstCustomerOfficalInfoJson>? = null,
    val lstVehicleJson: List<LstVehicleJson>? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerIdentityInfo(
    val IdentityDocument: String? = null,
    val IdentityID: Int? = null,
    val IdentityNo: String? = null,
    val IdentityType: String? = null,
    val IdentityTypeID: Int? = null,
    val IsNewIdentity: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerJson(
    val AccountComStatus: Int? = null,
    var RegStatus: Int? = null,
    val AccountNumber: String? = null,
    val AccountStatus: String? = null,
    val AcountHolderName: String? = null,
    val Address1: String? = null,
    val Address2: String? = null,
    val AddressId: Int? = null,
    val AgeGroupId: Int? = null,
    val Anniversary: Any? = null,
    val BankAddress: String? = null,
    val BankBranch: String? = null,
    val BankCountryId: Int? = null,
    val BankCountry: String? = null,
    val BankCountryName: String? = null,
    val BankName: String? = null,
    val BicSwiftCode: String? = null,
    val BsbAbaRoutingNumber: String? = null,
    val ChassisNumber: String? = null,
    val CityId: Int? = null,
    val CityName: String? = null,
    val ContractId: Int? = null,
    val CountryId: Int? = null,
    val CountryName: String? = null,
    val ContractFileName: String? = null,
    val ContractName: String? = null,
    val CurrencyId: Int? = null,
    val Currency: String? = null,
    val CustFamilyId: Int? = null,
    val CustomerCategoryId: Int? = null,
    val CustomerDetailId: Int? = null,
    val CustomerId: Int? = null,
    val CustomerRelationshipId: Int? = null,
    val CustomerRemarks: Any? = null,
    val CustomerType: String? = null,
    val CustomerTypeID: Int? = null,
    val Customer_Grade_ID: Int? = null,
    val District: String? = null,
    val DistrictId: Int? = null,
    val DistrictName: Any? = null,
    val Email: String? = null,
    val EmailStatus: Int? = null,
    val EnrollmentReferenceNumber: String? = null,
    val ExecutiveName: Any? = null,
    val FamilyMemberBirthday: Any? = null,
    val FamilyMemberName: Any? = null,
    val FirstName: String? = null,
    val Gender: Any? = null,
    val IsActive: Int? = null,
    val IFSCCode: Any? = null,
    val IbanNumber: String? = null,
    val IdentificationNo: String? = null,
    val IdentificationOthers: Any? = null,
    val IncomeRangeId: Int? = null,
    val InsuranceRenewalAmount: Int? = null,
    val IsBlackListed: Boolean? = null,
    val IsGradeVerified: Boolean? = null,
    val IsSmartphoneUser: Boolean? = null,
    val IsVerified: Int? = null,
    val IsWhatsappUser: Boolean? = null,
    val JCreatedDate: String? = null,
    val JDOB: String? = null,
    val JD_InvoiceNo: Any? = null,
    val JEnrollmentReferenceDate: Any? = null,
    val JInsuranceExpDate: Any? = null,
    val JJD_InvoiceDate: Any? = null,
    val JPolicyDate: Any? = null,
    val LIdentificationType: Int? = null,
    val LaborAmount: Int? = null,
    val Landmark: String? = null,
    val LanguageId: Int? = null,
    val LanguageName: Any? = null,
    val LastName: String? = null,
    val Locality: String? = null,
    val LocationCode: String? = null,
    val LocationId: Int? = null,
    val LocationName: String? = null,
    val LoyaltyId: String? = null,
    val MaritalStatus: Any? = null,
    val MerchantId: Int? = null,
    val Mobile: String? = null,
    val MobileNumberLimitation: Int? = null,
    val MobilePrefix: String? = null,
    val Mobile_Two: String? = null,
    val ModelNumber: String? = null,
    val NativeCountryId: Int? = null,
    val NativeStateId: Int? = null,
    val NativeStateName: String? = null,
    val Nominee: Any? = null,
    val NomineeDOB: Any? = null,
    val PolicyNumber: String? = null,
    val ProfessionId: Int? = null,
    val ProfilePicture: Any? = null,
    val ReferedBy: Any? = null,
    val RegType: String? = null,
    val Relationship: Any? = null,
    val ReligionID: Int? = null,
    val Remarks: String? = null,
    val StateId: Int? = null,
    val StateName: Any? = null,
    val TalukId: Int? = null,
    val TalukName: Any? = null,
    val TehsilBlockMandal: String? = null,
    val Title: String? = null,
    val UserId: Int? = null,
    val VehicleBrand: Any? = null,
    val VehicleID: Int? = null,
    val VehicleNumber: String? = null,
    val Village: String? = null,
    val WalletNumber: Any? = null,
    val WhatsAppNumber: Any? = null,
    val Zip: String? = null,
    val bankAccountVerifiedStatus: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerOfficalInfoJson(
    val CompanyName: String? = null,
    val DepartmentIdOfficial: Int? = null,
    val Designation: Any? = null,
    val DesignationIdOfficial: Int? = null,
    val FirmMobile: Any? = null,
    val FirmSize: Any? = null,
    val FirmTypeID: Int? = null,
    val GSTNumber: Any? = null,
    val IncorporationDate: Any? = null,
    val JobTypeID: Int? = null,
    val OfficalEmail: Any? = null,
    val OwnerName: String? = null,
    val PhoneOffice: String? = null,
    val PhoneResidence: Any? = null,
    val SAPCode: Any? = null,
    val StdCode: Any? = null,
    val StoreName: Any? = null
)

@JsonClass(generateAdapter = true)
data class LstVehicleJson (
    var JVehicleBrand: String? = null,
    var ModelNo: String? = null,
    var VehicleId: Int? = null,
    var VehicleNo: String? = null,
    var VinChasis: String? = null,
    var VehicleType: String? = null,
    var FuelType: String? = null
)