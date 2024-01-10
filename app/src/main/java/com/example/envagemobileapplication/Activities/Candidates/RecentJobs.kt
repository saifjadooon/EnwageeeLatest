package com.example.envagemobileapplication.Activities.Candidates

import BaseActivity
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Adapters.RecentJobsAdapter
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.ActivityRecentJobsBinding

class RecentJobs : BaseActivity() {

    lateinit var recentJobsAdapter: RecentJobsAdapter
    lateinit var binding: ActivityRecentJobsBinding
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    var search:String = " "
    var searchCheck = false
    val viewModel: CandidatesProfileSumViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observers()
        clickListeners()

    }

    private fun clickListeners() {
        binding.tvDone.setOnClickListener{
//            Toast.makeText(baseContext, "", Toast.LENGTH_SHORT).show()
            viewModel.assignRecentJobs(
                this,
                token,
                Constants.selectedJobslist
            )
            
        }

        binding.btnCross.setOnClickListener{

            if(Constants.selectedJobslist.size > 0){
                showConfirmationDialog()
            }else {
                Constants.selectedJobslist.clear()
                finish()
            }

        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                search = newText.orEmpty()


                Log.d("trimlen",search.trim().length.toString())

                if (search.trim().length >= 3) {
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

    private fun showConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to go back?")

        // Set the Yes button action
        alertDialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            // User clicked Yes
                Constants.selectedJobslist.clear()
                finish()

        })

        // Set the No button action
        alertDialogBuilder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        // Create and show the dialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun performSearch(search: String) {
        loader.show()
        viewModel.getRecentJobs(
            this,
            token,
            search
        )
    }


    private fun observers() {
        getRecentJobsObserver()
        assignedJobsOberver()
    }

    private fun assignedJobsOberver() {
        viewModel.LDassignRecentJobs.observe(this) {
            loader.hide()
            if (it.data != null) {
                Constants.selectedJobslist.clear()
                Toast.makeText(applicationContext, "Job added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getRecentJobsObserver() {
        viewModel.LDgetRecentJobs.observe(this) {
            loader.hide()

            if (it.data != null) {

                if(searchCheck == false){

                    var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.Datum> = ArrayList()
                    (it.data?.let { arraylist.addAll(it) })

                    if(arraylist.size >0 ){
                        binding.tvNoDataRecentJobs.isVisible = false
                        binding.rvRecentJobs.isVisible = true
                        setupJobsAdapter(arraylist)
                    }else{
                        binding.rvRecentJobs.isVisible = false
                        binding.tvNoDataRecentJobs.isVisible = true
                    }
                }else{

                    var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.Datum> = ArrayList()
                    arraylist.clear()
                    (it.data?.let { arraylist.addAll(it) })

                    if(arraylist.size >0 ){
                        binding.tvNoDataRecentJobs.isVisible = false
                        binding.rvRecentJobs.isVisible = true
                        setupJobsAdapter(arraylist)
                    }else{
                        binding.rvRecentJobs.isVisible = false
                        binding.tvNoDataRecentJobs.isVisible = true
                    }

                }



            } else {
                loader.hide()
                Toast.makeText(this, "no recent jobs found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setupJobsAdapter(
        candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.Datum>
    ) {

        binding!!.rvRecentJobs.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            this
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvRecentJobs.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        recentJobsAdapter = RecentJobsAdapter(
            this,
            candidatelist,
            supportFragmentManager
        )
        binding!!.rvRecentJobs.adapter = recentJobsAdapter

    }

    private fun initViews() {
        loader = Loader(this)
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken()!!


        loader.show()
        viewModel.getRecentJobs(
            this,
            token,
            search
        )
    }
}