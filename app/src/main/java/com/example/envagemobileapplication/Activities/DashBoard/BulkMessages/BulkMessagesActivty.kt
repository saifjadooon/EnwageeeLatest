package com.example.envagemobileapplication.Activities.DashBoard.BulkMessages

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.DashBoard.BulkMessages.BulkMessagesFragments.FilterRecipetentsF
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.BulkMessagesViewModel
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.ActivityBulkMessagesActivtyBinding

class BulkMessagesActivty : AppCompatActivity() {
    var global = com.example.envagemobileapplication.Utils.Global
    val viewModel: BulkMessagesViewModel by viewModels()
    lateinit var tokenManager: TokenManager
    lateinit var binding: ActivityBulkMessagesActivtyBinding
    lateinit var loader: Loader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBulkMessagesActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = Loader(this)
        initViews()
        clickListeners()
        observers()
    }

    private fun observers() {
        viewModel.LDGetTwilioConfig.observe(this) {
            loader.hide()
            global.twilioResp = it.data
        }
    }

    private fun clickListeners() {
        binding.ivcCross.setOnClickListener { showDialog() }
    }

    private fun initViews() {



        tokenManager = TokenManager(this@BulkMessagesActivty)
        var token = tokenManager.getAccessToken().toString()
        viewModel.getTwilioConfigration(this, tokenManager.getAccessToken())

    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            this.finish()// Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }



}