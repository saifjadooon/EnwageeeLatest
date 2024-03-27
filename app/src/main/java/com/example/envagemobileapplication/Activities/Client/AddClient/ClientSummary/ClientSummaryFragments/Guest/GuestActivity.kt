package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest

import BaseActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.AddGuestFragments.AddGuestAddressDetailF
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.AddGuestFragments.AddGuestBasicDetailF
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.AddGuestFragments.AddGuestDescriptionF
import com.example.envagemobileapplication.Models.RequestModels.GuestsData
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddGuestResponse.AddGuestResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.AddGuestFragmentsDataListener
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityGuestBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestActivity : BaseActivity(), AddGuestFragmentsDataListener {
    companion object {
        lateinit var binding: ActivityGuestBinding
        private var selectedItem: View? = null
    }

    private var itemView: View? = null
    private var descriptionfinal: MultipartBody.Part? = null
    var descriptiontext: String = ""

    var global = com.example.envagemobileapplication.Utils.Global


    lateinit var loader: Loader
    private val guestData = GuestsData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = Loader(this)
        replaceFragment(AddGuestBasicDetailF())
        var itemList: ArrayList<String> = ArrayList()
        itemList.add("Basic Details")
        itemList.add("Address Details")
        itemList.add("Description")
        com.example.envagemobileapplication.Utils.Global.guestData = guestData
        for (itemText in itemList) {
              itemView = layoutInflater.inflate(R.layout.hsv_candidates_item, null)

            val textView = itemView!!.findViewById<TextView>(R.id.iv_title)
            if (itemText == "Basic Details") {
                // Set the background for "Profile summary" by default
                itemView!!.setBackgroundResource(R.drawable.ic_bg_underline)
                selectedItem = itemView
            }

            textView.text = itemText

           /* itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    // Reset the background of the previously selected item
                    selectedItem?.setBackgroundResource(R.drawable.btn_white_radius)

                    // Highlight the selected item
                    itemView.setBackgroundResource(R.drawable.gray_bg)
                    selectedItem = itemView

                    val clickedText = itemText
                    // Get the left position of the clicked item relative to its parent
                    val left = itemView.left

                    // Scroll the ScrollView to bring the clicked item into view
                    binding.horizontalScrollView.scrollTo(left, 0)

                    // Give focus to the clicked item's view
                    itemView.requestFocus()

                    if (clickedText == "Basic Details") {
                        replaceFragment(AddGuestBasicDetailF())
                    } else if (clickedText.equals("Address Details")) {

                        replaceFragment(AddGuestAddressDetailF())
                    } else if (clickedText.equals("Description")) {

                        replaceFragment(AddGuestDescriptionF())
                    }
                }
            })*/

            binding.linearlist.addView(itemView)
        }

        binding.btnsave.setOnClickListener {
            callingAddGuestAPi()
        }

    }

    private fun callingAddGuestAPi() {
        var tokenmanager = TokenManager(this)
        var token = tokenmanager.getAccessToken().toString()
        Log.i("token", token.toString())
        descriptiontext = global.descriptiontextguest!!
        if (!descriptiontext.isNullOrBlank()) {
            val htmlContent = descriptiontext
            val mediaType = "text/html".toMediaTypeOrNull()
            val descriptionBody = RequestBody.create(mediaType, htmlContent)
            val descriptionPart =
                MultipartBody.Part.createFormData(
                    "description",
                    "description.html",
                    descriptionBody
                )
            descriptionfinal = descriptionPart

        } else {

            val htmlContent = "<p></p>"
            val mediaType = "text/html".toMediaTypeOrNull()
            val descriptionBody = RequestBody.create(mediaType, htmlContent)
            val descriptionPart =
                MultipartBody.Part.createFormData(
                    "description",
                    "description.html",
                    descriptionBody
                )
            descriptionfinal = descriptionPart
        }

        var description = descriptionfinal
        var clientId = MultipartBody.Part.createFormData("clientId", "1513")
        //var profileImage = MultipartBody.Part.createFormData("profileImage", "")
        var profileImagee = global.profilePicGuest
        var firstName =
            MultipartBody.Part.createFormData("firstName", guestData.firstName.toString())
        var lastName = MultipartBody.Part.createFormData("lastName", guestData.lastName.toString())
        var emailAddress =
            MultipartBody.Part.createFormData("emailAddress", guestData.email.toString())
        var gender = MultipartBody.Part.createFormData("gender", guestData.gender.toString())
        var department =
            MultipartBody.Part.createFormData("department", guestData.department.toString())
        var phoneNumber =
            MultipartBody.Part.createFormData("phoneNumber", guestData.phonenumber.toString())
        var linkedInUrl =
            MultipartBody.Part.createFormData("linkedInUrl", guestData.linkedinurl.toString())
        var addressLine1 =
            MultipartBody.Part.createFormData("addressLine1", guestData.address1.toString())
        var addressLine2 =
            MultipartBody.Part.createFormData("addressLine2", guestData.address2.toString())
        var country = MultipartBody.Part.createFormData("country", guestData.country.toString())
        var zipCode = MultipartBody.Part.createFormData("zipCode", guestData.zipcode.toString())
        var city = MultipartBody.Part.createFormData("city", guestData.city.toString())
        var state = MultipartBody.Part.createFormData("state", guestData.state.toString())
        var location = MultipartBody.Part.createFormData("location", guestData.location.toString())
        try {
            loader.show()

            ApiUtils.getAPIService(this).AddGuest(
                token.toString(),
                description!!,
                clientId,
                profileImagee,
                firstName,
                lastName,
                emailAddress,
                gender,
                department,
                phoneNumber,
                linkedInUrl,
                addressLine1,
                addressLine2,
                country,
                zipCode,
                city,
                state,
                location
            )
                .enqueue(object : Callback<AddGuestResponse> {
                    override fun onResponse(
                        call: Call<AddGuestResponse>,
                        response: Response<AddGuestResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            if (response?.body()!!.data.alreadyExist.equals(true)) {
                                loader.hide()

                                Snackbar.make(
                                    binding.root,
                                    "Client already exists.",
                                    Snackbar.LENGTH_LONG
                                ).apply {

                                    setTextColor(
                                        ContextCompat.getColor(
                                            this@GuestActivity,
                                            R.color.white
                                        )
                                    )
                                    setBackgroundTint(
                                        ContextCompat.getColor(
                                            this@GuestActivity,
                                            R.color.orange
                                        )
                                    )
                                    show()
                                }
                            } else {

                                val toast = Toast.makeText(
                                    this@GuestActivity,
                                    "Client Added successfully",
                                    Toast.LENGTH_LONG
                                )
                                toast.show()

                            }
                        } else {
                            Log.i("errormsg", response.message())
                        }
                    }

                    override fun onFailure(call: Call<AddGuestResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }


    }

    override fun onDataReceived(
        firstname: String?,
        lastname: String?,
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
    ) {
        if (firstname != null) {
            guestData.firstName = firstname
            Log.i("firstname = ", firstname)
        }
        if (lastname != null) {
            guestData.lastName = lastname
            Log.i("lastName = ", lastname.toString())
        }
        if (email != null) {
            guestData.email = email
            Log.i("email = ", email.toString())
        }
        if (gender != null) {
            guestData.gender = gender
            Log.i("gender = ", gender.toString())

        }
        if (department != null) {
            guestData.department = department
            Log.i("department = ", department.toString())

        }
        if (phonenumber != null) {
            guestData.phonenumber = phonenumber
            Log.i("phonenumber = ", phonenumber.toString())

        }
        if (linkedinurl != null) {
            guestData.linkedinurl = linkedinurl
            Log.i("linkedinurl = ", linkedinurl.toString())

        }
        if (address1 != null) {
            guestData.address1 = address1
            Log.i("address1 = ", email.toString())

        }
        if (address2 != null) {
            guestData.address2 = address2
            Log.i("address2 = ", email.toString())

        }
        if (country != null) {
            guestData.country = country
            Log.i("country = ", country.toString())

        }
        if (zipcode != null) {
            guestData.zipcode = zipcode
            Log.i("zipcode = ", zipcode.toString())

        }
        if (city != null) {
            guestData.city = city
            Log.i("city = ", city.toString())

        }
        if (state != null) {
            guestData.state = state
            Log.i("state = ", state.toString())

        }
        if (location != null) {
            guestData.location = location
            Log.i("location = ", location.toString())

        }
        if (description != null) {
            guestData.description = description
            Log.i("description = ", description.toString())

        }


    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.cc_fragments, fragment)
        transaction.commit()
    }
}