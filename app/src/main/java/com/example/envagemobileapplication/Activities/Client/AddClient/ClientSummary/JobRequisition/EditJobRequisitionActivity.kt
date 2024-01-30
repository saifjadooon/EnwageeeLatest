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
    companion object {

        var selectedFragmentTag: String? = null
        fun showFragment(fragmentManager: FragmentManager, tag: String) {
            val fragmentToShow = fragmentManager.findFragmentByTag(tag)
            if (fragmentToShow != null) {
                val transaction = fragmentManager.beginTransaction()
                transaction.show(fragmentToShow)
                transaction.addToBackStack(null)
                transaction.commit()
                selectedFragmentTag = tag
            }
        }

        fun hideFragment(fragmentManager: FragmentManager, tag: String) {
            val fragmentToHide = fragmentManager.findFragmentByTag(tag)
            if (fragmentToHide != null) {
                val transaction = fragmentManager.beginTransaction()
                transaction.hide(fragmentToHide)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

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