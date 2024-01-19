package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.AssessmentFormAdapter
import com.example.envagemobileapplication.Models.RequestModels.GetAssessmentFormsRM
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentBottomSheetSendAssessmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetSendAssessment : BottomSheetDialogFragment() {

    val viewModel: JobSummaryViewModel by viewModels()
    lateinit var binding: FragmentBottomSheetSendAssessmentBinding
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var adapter: AssessmentFormAdapter
    lateinit var loader: Loader
    var search:String = " "
    var searchCheck = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetSendAssessmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observers()


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                search = newText.orEmpty()
                if (search.length >= 3) {
                    searchCheck = true
                    performSearch(search)
                } else if (search.isEmpty()) {
                    searchCheck = false
                    // Handle resetting the search when the query is empty
                    performSearch(" ")
                }

                return true
            }
        })

    }

    private fun performSearch(search: String) {
        loader.show()

        val payload = Constants.AssessmentClientId?.let {
            Constants.AssessmentJobId?.let { it1 ->
                GetAssessmentFormsRM(
                    it1,
                    it,
                    search
                )
            }
        }

        viewModel.getAssessmentForms(
            this,
            token,
            payload
        )

    }

    private fun observers() {


        viewModel.LDgetAssessmentForms.observe(this) {
            loader.hide()

            if (it.data != null) {

                if(searchCheck == false){

                    var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentForms.Datum> = ArrayList()
                    (it.data?.let { arraylist.addAll(it) })

                    if(arraylist.size >0 ){
                        setUpAssessmentsAdapter(arraylist,requireContext())
                    }
                }else{

                    var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentForms.Datum> = ArrayList()
                    arraylist.clear()
                    (it.data?.let { arraylist.addAll(it) })
                    setUpAssessmentsAdapter(arraylist,requireContext())

                }



            } else {
                loader.hide()
                Toast.makeText(context, "no assessment found", Toast.LENGTH_SHORT).show()
            }
        }


//        viewModel.LDgetAssessmentForms.observe(viewLifecycleOwner) {
//
//            if (it.data != null) {
//
//                var arraylist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssessmentForms.Datum> =
//                    ArrayList()
//                it?.data?.let { arraylist.addAll(it) }
//                setUpAssessmentsAdapter(arraylist, requireContext())
//
//            } else {
//                Toast.makeText(context, "data not found success", Toast.LENGTH_SHORT).show()
//            }
//        }




        viewModel.LDsendAssessmentResponse?.observe(viewLifecycleOwner) { response ->
            try {


                if (response?.data != null) {
                    Toast.makeText(context, response.data.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    (context as Activity).finish()
                    dismiss()
                } else {
                    loader.hide()
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                loader.hide()
                Log.e("jkbhdjhsbdhjsbhjd", "Error in LDsendAssessmentResponse observer", e)
            }
        }
    }


    fun setUpAssessmentsAdapter(
        assessmentsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentForms.Datum>,
        requireContext: Context
    ) {

        try {
            binding.search.isFocusable = false
            binding.search.isFocusableInTouchMode = false
            binding!!.rvAssessments.setHasFixedSize(true)
            val mLayoutManager = LinearLayoutManager(
                requireContext
            )
            mLayoutManager.reverseLayout = true
            mLayoutManager.stackFromEnd = true
            binding!!.rvAssessments.layoutManager = LinearLayoutManager(
                requireContext,
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = AssessmentFormAdapter(
                requireContext,
                assessmentsList,
                childFragmentManager,
                viewModel
            )
            binding!!.rvAssessments.adapter = adapter
        } catch (e: Exception) {
            Log.d("yaseeeeen", e.toString())
        }


    }

    private fun initViews() {

        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
        loader = Loader(requireContext())


        val payload = Constants.AssessmentClientId?.let {
            Constants.AssessmentJobId?.let { it1 ->
                GetAssessmentFormsRM(
                    it1,
                    it,
                    "a"
                )
            }
        }


        viewModel.getAssessmentForms(
            this,
            token,
            payload
        )
    }


}