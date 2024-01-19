package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition.EditJobRequisitionFragments

import android.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition.EditJobRequisitionActivity
import com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet.BSheetAddjobClientList
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobBasicDetailRequestModel
import com.example.envagemobileapplication.Models.RequestModels.PaygroupRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CustomTemplateResponse.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.PayGroupResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobRequisitionViewModel
import com.example.envagemobileapplication.databinding.FragmentBasicDetaialBinding

class BasicDetaialF : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    val maxLength = 200
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        if (dstart == 0 && end > start && source[start] == ' ') {
            return@InputFilter ""
        }
        null
    }
    private var industryFinal: Int = 0
    lateinit var industryList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetIndustryListResponse.Datum>
    var paygroupIDFinal: Int = 0
    var payGroupListresponse: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.Datum> =
        ArrayList()
    private var clientidFinal: Int = 0
    lateinit var templateResponse: MutableList<Datum>
    lateinit var loader: Loader
    lateinit var clientid: String
    lateinit var tokenmanager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentBasicDetaialBinding
    val viewModel: JobRequisitionViewModel by activityViewModels()
    val bottomSheetFragment = BSheetAddjobClientList()
    lateinit var basicDetailsRM: AddJobBasicDetailRequestModel
    var jobTemplateId = ""
    var global = com.example.envagemobileapplication.Utils.Global

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBasicDetaialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initviews()
        clicklistners()
        networkCalls()
        observers()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            global.showDialog(requireContext(), requireActivity())
        }

    }

    private fun networkCalls() {
        loader.show()
        var jobid = 1263
        var clientid = 1411

        viewModel.getjobReqByID(requireContext(), token, jobid.toString())

        handler.postDelayed({
            var model = PaygroupRequestModel(clientid, "a")
            viewModel.searchPayGroupApi(requireContext(), token, model)
        }, 500)

        handler.postDelayed({
            viewModel.getindustry(requireContext(), token)
        }, 1000)
    }

    private fun observers() {
        viewModel.LDpayGroup.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {

                try {
                    payGroupListresponse = ArrayList()
                    var paygrouplist: ArrayList<String> =
                        ArrayList()
                    var paygroupresponselist: ArrayList<PayGroupResponse> = ArrayList()
                    for (i in 0 until it.data.size) {
                        paygroupresponselist.add(it)
                        payGroupListresponse.add(it.data.get(i))
                        paygrouplist.add(
                            it.data.get(i).payrollPayGroupTitle + " | " + it.data.get(
                                i
                            ).payrollPayGroupId
                        )
                    }

                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        paygrouplist
                    )
                    binding.spinnerPaygroup.setAdapter(adapter)
                    binding.TIpaygroup.error = null
                    if (global.jobReqbyJobid!!.payrollPayGroupId != null) {
                        for (i in 0 until paygroupresponselist.size) {
                            for (j in 0 until paygroupresponselist.get(i).data.size) {
                                if (global.jobReqbyJobid!!.payrollPayGroupId.equals(
                                        paygroupresponselist.get(
                                            i
                                        ).data.get(j).payrollPayGroupId
                                    )
                                ) {
                                    binding.spinnerPaygroup.setText(
                                        paygroupresponselist.get(i).data.get(j).payrollPayGroupTitle + " | " + paygroupresponselist.get(
                                            i
                                        ).data.get(j).payrollPayGroupId
                                    )
                                }
                            }
                        }
                    } else {
                        binding.spinnerPaygroup.setText(
                            it.data.get(0).payrollPayGroupTitle + " | " + it.data.get(
                                0
                            ).payrollPayGroupId
                        )
                    }


                } catch (e: Exception) {
                }


            }
        }
        viewModel.LDgetIndustry.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {
                industryList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    industryList.add(it.data.get(i))
                }

                var industrynames: ArrayList<String> = ArrayList()
                for (i in 0 until industryList.size) {
                    industrynames.add(industryList.get(i).name.toString())
                }
                try {

                    if (global.jobReqbyJobid!!.industryId != null) {
                        for (i in 0 until industryList.size) {
                            if (industryList.get(i).industryId.equals(global.jobReqbyJobid!!.industryId)) {
                                binding.spinnerIndustry.setText(industryList.get(i).name.toString())
                            }
                        }
                    }
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        industrynames
                    )


                    binding.spinnerIndustry.setAdapter(adapter)


                } catch (e: Exception) {

                }


            }
        }
        viewModel.LDgetJobRequestResp.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {

                global.jobReqbyJobid = it.data
                if (it.data.positionName != null) {
                    binding.etPosition.setText(it.data.positionName)
                }
                if (it.data.jobNature != null) {
                    binding.spinnerJobNature.setText(it.data.jobNature)
                }

            }
        }
    }

    private fun clicklistners() {

        binding.spinnerPaygroup.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerPaygroup.isPopupShowing) {
                binding.spinnerPaygroup.dismissDropDown()
            } else {
                binding.spinnerPaygroup.showDropDown()
            }
            false
        })


        binding.spinnerIndustry.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerIndustry.isPopupShowing) {
                binding.spinnerIndustry.dismissDropDown()
            } else {
                var industrynames: ArrayList<String> = ArrayList()
                for (i in 0 until industryList.size) {
                    industrynames.add(industryList.get(i).name.toString())
                }
                val adapter = customadapter(
                    requireContext(),
                    R.layout.simple_spinner_item,
                    industrynames
                )
                binding.spinnerIndustry.setAdapter(adapter)
                binding.spinnerIndustry.showDropDown()
            }
            false
        })

        binding.TIindustry.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->


                if (binding.spinnerIndustry.isPopupShowing) {
                    binding.spinnerIndustry.dismissDropDown()
                } else {
                    var industrynames: ArrayList<String> = ArrayList()
                    for (i in 0 until industryList.size) {
                        industrynames.add(industryList.get(i).name.toString())
                    }
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        industrynames
                    )
                    binding.spinnerIndustry.setAdapter(adapter)
                    binding.spinnerIndustry.showDropDown()

                }
            }

        binding.TIpaygroup.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (binding.spinnerPaygroup.isPopupShowing) {
                    binding.spinnerPaygroup.dismissDropDown()
                } else {
                    binding.spinnerPaygroup.showDropDown()
                }
            }

        binding.TIjobNature.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (binding.spinnerJobNature.isPopupShowing) {
                    binding.spinnerJobNature.dismissDropDown()
                } else {
                    var jobNatureList: ArrayList<String> = ArrayList()
                    jobNatureList.add("On-Site")
                    jobNatureList.add("Remote")
                    jobNatureList.add("Hybrid")
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        jobNatureList
                    )
                    binding.spinnerJobNature.setAdapter(adapter)
                    binding.spinnerJobNature.showDropDown()
                }
            }

        binding.spinnerJobNature.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerJobNature.isPopupShowing) {
                binding.spinnerJobNature.dismissDropDown()
            } else {
                var jobNatureList: ArrayList<String> = ArrayList()
                jobNatureList.add("On-Site")
                jobNatureList.add("Remote")
                jobNatureList.add("Hybrid")
                val adapter = customadapter(
                    requireContext(),
                    R.layout.simple_spinner_item,
                    jobNatureList
                )
                binding.spinnerJobNature.setAdapter(adapter)
                binding.spinnerJobNature.showDropDown()
            }
            false
        })

        binding.btnNext.setOnClickListener {

            var positioname = binding.etPosition.text.toString()

            var paygroup = binding.spinnerPaygroup.text.toString()
            var industry = binding.spinnerIndustry.text.toString()



            for (i in 0 until payGroupListresponse.size) {
                var paygroupfinal =
                    payGroupListresponse.get(i).payrollPayGroupTitle + " | " + payGroupListresponse.get(
                        i
                    ).payrollPayGroupId
                if (paygroupfinal == paygroup) {
                    paygroupIDFinal = payGroupListresponse.get(i).payrollPayGroupId
                }
            }

            for (i in 0 until industryList.size) {
                if (industryList.get(i).name.equals(industry)) {
                    industryFinal = industryList.get(i).industryId
                }
            }

            var clientid = clientidFinal
            var paygroupid = paygroupIDFinal
            var industryid = industryFinal
            var jobnature = binding.spinnerJobNature.text.toString()
            var jobId = 0

            basicDetailsRM = AddJobBasicDetailRequestModel(
                positionName = positioname,
                clientId = clientid,
                payrollPayGroupId = paygroupid,
                jobId = jobId,
                industryId = industryid,
                jobNature = jobnature,
                useTemplate = "",
                jobTemplateId = jobTemplateId
            )

            com.example.envagemobileapplication.Utils.Global.basicDetailReqModel = basicDetailsRM
            if (!positioname.isNullOrEmpty() && !paygroup.isNullOrEmpty()) {

                EditJobRequisitionActivity.binding.ivOne.setImageDrawable(
                    requireContext().getDrawable(
                        com.example.envagemobileapplication.R.drawable.ic_basic_detail_done
                    )
                )
                replaceFragment(JobDetailF())

            } else {

                if (positioname.isNullOrEmpty()) {
                    binding.TIposition.error = "Position name is Required."
                    binding.TIposition.errorIconDrawable = null// Set the error message
                    binding.TIposition.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }

                if (paygroup.isNullOrEmpty()) {
                    binding.TIpaygroup.error = "Paygroup is Required.."
                    binding.TIpaygroup.errorIconDrawable = null// Set the error message
                    binding.TIpaygroup.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
            }
        }

        binding.etPosition.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the length exceeds maxLength and trim the text if necessary
                if (s != null && s.length > maxLength) {
                    binding.etPosition.setText(s.subSequence(0, maxLength))
                    binding.etPosition.setSelection(maxLength) // Move the cursor to the end
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    binding.TIposition.error = "Position name is Required."
                    binding.TIposition.errorIconDrawable = null// Set the error message
                    binding.TIposition.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                } else if (enteredText.isNotEmpty()) {
                    // URL is valid, you can update UI or perform other actions
                    binding.TIposition.error = null // Clear any previous error
                }
            }
        })
    }

    private fun initviews() {


        loader = Loader(requireContext())
        clientid = Constants.clientid.toString()
        tokenmanager = TokenManager(requireActivity())
        token = tokenmanager.getAccessToken()!!
        binding.etPosition.filters = arrayOf(filter)

        val hint = "Position Name *"
        val formattedHint =
            Constants.formatHintWithRedAsterisk(hint)
        binding.TIposition.hint = formattedHint


        val paygrouphint = "Pay Group *"
        val formatedpaygroup =
            Constants.formatHintWithRedAsterisk(
                paygrouphint
            )
        binding.TIpaygroup.hint = formatedpaygroup


        var jobNatureList: ArrayList<String> = ArrayList()
        jobNatureList.add("On-Site")
        jobNatureList.add("Remote")
        jobNatureList.add("Hybrid")

        val adapter = customadapter(
            requireContext(),
            R.layout.simple_spinner_item,
            jobNatureList
        )
        binding.spinnerJobNature.setAdapter(adapter)
        binding.spinnerJobNature.setText(jobNatureList.get(0))


    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            com.example.envagemobileapplication.R.id.cc_editJobRequisition,
            fragment
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

}