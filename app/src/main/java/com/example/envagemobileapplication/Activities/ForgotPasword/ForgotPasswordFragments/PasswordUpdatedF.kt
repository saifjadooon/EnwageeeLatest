package com.example.envagemobileapplication.Activities.ForgotPasword.ForgotPasswordFragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Activities.Login.LoginActivty
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentPasswordUpdatedBinding


class PasswordUpdatedF : Fragment() {
    lateinit var binding: FragmentPasswordUpdatedBinding
    lateinit var tokenManager: TokenManager
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var loader: Loader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentPasswordUpdatedBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    private fun initViews() {
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())

    }

    private fun clickListeners() {

        binding.login.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivty::class.java)
            requireActivity().finishAffinity()
            startActivity(intent)
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            com.example.envagemobileapplication.R.id.cc_forgotpassword,
            fragment
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }


}