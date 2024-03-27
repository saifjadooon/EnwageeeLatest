package com.example.envagemobileapplication.Models.RequestModels

import okhttp3.MultipartBody

data class GuestsData(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var department: String? = null,
    var phonenumber: String? = null,
    var linkedinurl: String? = null,
    var address1: String? = null,
    var address2: String? = null,
    var country: String? = null,
    var zipcode: String? = null,
    var city: String? = null,
    var state: String? = null,
    var location: String? = null,
    var description: String? = null
)
