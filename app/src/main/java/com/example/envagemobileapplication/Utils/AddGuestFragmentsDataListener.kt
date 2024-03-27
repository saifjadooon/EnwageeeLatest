package com.example.envagemobileapplication.Utils

import okhttp3.MultipartBody


interface AddGuestFragmentsDataListener {
    fun onDataReceived(
        firstName: String?,
        lastName: String?,
        email: String?,
        gender: String?,
        department: String?,
        phonenumber: String?,
        linkedinurl: String?,
        address1: String?,
        address2: String?,
        country: String?,
        zipcode: String?,
        city: String?,
        state: String?,
        location: String?,
        description: String?

    )
}