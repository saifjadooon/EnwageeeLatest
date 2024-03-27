package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.AddGuestFragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCountrylistResponse.GetCountrylistResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.ZipCodeResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.AddGuestFragmentsDataListener
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentAddGuestAddressDetailBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddGuestAddressDetailF : Fragment() {
    private var dataListener: AddGuestFragmentsDataListener? = null
    lateinit var binding: FragmentAddGuestAddressDetailBinding
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var tokenManager: TokenManager
     lateinit var loader: Loader


    override fun onPause() {

        val address1 = binding.etAdress1.text.toString()
        val address2 = binding.etAdress2.text.toString()
        val country = binding.spinnerCountry.text.toString()
        val zipcode = binding.etZipcode.text.toString()
        val city = binding.etCity.text.toString()
        val state = binding.etState.text.toString()
        val location = binding.etLocation.text.toString()
        // Call the interface method with the data
        dataListener?.onDataReceived(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            address1,
            address2,
            country,
            zipcode,
            city,
            state,
            location,
            null
        )
         super.onPause()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddGuestFragmentsDataListener) {
            dataListener = context
        }
    }

    override fun onResume() {

        if (global.guestData.address1 != null) {
            binding.etAdress1.setText(global.guestData.address1.toString())
        }
        if (global.guestData.address2 != null) {
            binding.etAdress2.setText(global.guestData.address2.toString())
        }

        if (global.guestData.country != null) {
            binding.spinnerCountry.setText(global.guestData.country.toString())
        }
        if (global.guestData.zipcode != null) {
            binding.etZipcode.setText(global.guestData.zipcode.toString())
        }
        if (global.guestData.city != null) {
            binding.etCity.setText(global.guestData.city.toString())
        }
        if (global.guestData.state != null) {
            binding.etState.setText(global.guestData.state.toString())
        }
        if (global.guestData.location != null) {
            binding.etLocation.setText(global.guestData.location.toString())
        }
        super.onResume()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentAddGuestAddressDetailBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    private fun initViews() {
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())
        var token = tokenManager.getAccessToken().toString()
        getCountrylist(requireContext(), token)
        binding.etAdress1.filters = arrayOf(global.filter)
        binding.etAdress2.filters = arrayOf(global.filter)
        binding.etLocation.filters = arrayOf(global.filter)
        binding.etZipcode.filters = arrayOf(global.zipcodefilterl, global.maxLengthFilter)


    }

    private fun clickListeners() {

        binding.btnNext.setOnClickListener{
            replaceFragment(AddGuestDescriptionF())
        }
        binding.etZipcode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is not used in this example.
            }
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // This method is not used in this example.
            }
            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.length == 5) {
                    try {

                        val imm =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(view!!.windowToken, 0)
                        if (binding.etZipcode.text.toString() != "" && binding.etZipcode.text.length >= 5) {
                            var zipcodeEnterd = binding.etZipcode.text.toString()
                            getzipcityStatelist(zipcodeEnterd)
                        } else if (binding.etZipcode.text.length < 5) {
                            binding.TIzipcode.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                            binding.TIzipcode.errorIconDrawable = null
                            binding.TIzipcode.error = "Zip Code must be 5 digits"
                        } else {
                            binding.TIzipcode.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                            binding.TIzipcode.errorIconDrawable = null
                            binding.TIzipcode.error = "Zip Code is required."
                        }
                    } catch (e: Exception) {
                    }

                } else {
                    binding.etState.setText("")
                    binding.etCity.setText("")
                }
            }
        })
        binding.spinnerCountry.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerCountry.isPopupShowing) {
                binding.spinnerCountry.dismissDropDown()
            } else {
                binding.spinnerCountry.showDropDown()
            }
            false
        })
    }

    private fun getzipcityStatelist(zipcodeEnterd: String) {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val zipcode = zipcodeEnterd

        try {
            ApiUtils.getAPIService(requireContext()).getzipcityStatelist(
                token.toString(), zipcode
            )
                .enqueue(object : Callback<ZipCodeResponse> {
                    override fun onResponse(
                        call: Call<ZipCodeResponse>,
                        response: Response<ZipCodeResponse>
                    ) {
                        loader.hide()
                        if (response.body()?.data!!.size > 0) {


                            var zipcodedata: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.Datum> =
                                ArrayList()
                            for (i in 0 until response.body()!!.data.size) {

                                zipcodedata.add(response.body()!!.data.get(i))
                            }

                            binding.etState.setText(zipcodedata.get(0).stateName)
                            binding.etCity.setText(zipcodedata.get(0).cityName)

                            //    callUpdateClientApi(wesite)
                        } else {
                            val rootView = binding.root

                            val message = "No Record Found"
                            val duration = Snackbar.LENGTH_SHORT

                            val snackbar = Snackbar.make(rootView, message, duration)
                            snackbar.setActionTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    com.example.envagemobileapplication.R.color.red
                                )
                            )
                            snackbar.show()
                        }
                    }

                    override fun onFailure(call: Call<ZipCodeResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())
                        loader.hide()
                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exception", ex.toString())
            loader.hide()
        }
    }
    private fun getCountrylist(context: Context, token: String) {
        try {
            loader.show()
            ApiUtils.getAPIService(context).GetCountryList(

                token.toString(),
            )
                .enqueue(object : Callback<GetCountrylistResponse> {
                    override fun onResponse(
                        call: Call<GetCountrylistResponse>,
                        response: Response<GetCountrylistResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            var countryList: ArrayList<String> = ArrayList()
                            for (i in 0 until response.body()?.data!!.size) {
                                countryList.add(
                                    response.body()!!.data.get(i).name
                                )
                            }
                            try {
                                val adapter = customadapter(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    countryList
                                )
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                binding.spinnerCountry.setAdapter(adapter)
                            } catch (e: Exception) {

                            }

                        }
                    }

                    override fun onFailure(
                        call: Call<GetCountrylistResponse>,
                        t: Throwable
                    ) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.show()
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }
/*
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = childFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.envagemobileapplication.R.id.cc_fragments, fragment)
        transaction.commit()
    }
*/

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            com.example.envagemobileapplication.R.id.cc_fragments,
            fragment
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }
}