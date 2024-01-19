package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition

import BaseActivity
 import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition.EditJobRequisitionFragments.BasicDetaialF
 import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.ActivityEditJobRequisitionBinding

class EditJobRequisitionActivity : BaseActivity() {

    var global = com.example.envagemobileapplication.Utils.Global
    companion object{
        lateinit var binding: ActivityEditJobRequisitionBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditJobRequisitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initviews()
        clicklisteners()
    }

    private fun clicklisteners() {
        binding.ivCross2.setOnClickListener {
           global.showDialog(this,this)

        }
    }

    private fun initviews() {
        replaceFragment(BasicDetaialF())
    }


    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.cc_editJobRequisition, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}