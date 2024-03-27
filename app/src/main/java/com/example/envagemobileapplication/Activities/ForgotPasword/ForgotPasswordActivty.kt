package com.example.envagemobileapplication.Activities.ForgotPasword

import BaseActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.GuestActivity
import com.example.envagemobileapplication.Activities.ForgotPasword.ForgotPasswordFragments.EnterEmailF
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityForgotPasswordActivtyBinding

class ForgotPasswordActivty : BaseActivity() {
    lateinit var binding: ActivityForgotPasswordActivtyBinding
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var loader: Loader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_activty)
        binding = ActivityForgotPasswordActivtyBinding.inflate(layoutInflater)
        loader = Loader(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        replaceFragment(EnterEmailF())
        setContentView( binding.root)

    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.cc_forgotpassword, fragment)
        transaction.commit()
    }
}