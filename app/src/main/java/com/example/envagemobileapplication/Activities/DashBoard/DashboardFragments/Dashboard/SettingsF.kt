package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.Dashboard


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.DashBoard.SettingsDetails.ChangePasswordActivity
import com.example.envagemobileapplication.Activities.DashBoard.SettingsDetails.PersonalInformationActivity
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentSettingsBinding

class SettingsF : Fragment() {
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentSettingsBinding
    val viewModel: MainActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.getRoot()
    }

    private fun clickListeners() {

        binding.ivProfilepill.setOnClickListener {
            val up = R.drawable.ic_profilepillarrowup
            val down = R.drawable.ic_profilepil

            // Get the current resource ID of the ImageView
            val currentResource = binding.ivProfilepill.tag as? Int

            // Check the current resource and set the appropriate image
            if (currentResource == up) {
                // If the current image is 'up', set 'down'
                binding.ccProfilepills.visibility = View.GONE
                binding.ivProfilepill.setImageResource(down)
                binding.ivProfilepill.tag = down
            } else {
                // If the current image is not 'up' or unknown, set 'up'
                binding.ccProfilepills.visibility = View.VISIBLE
                binding.ivProfilepill.setImageResource(up)
                binding.ivProfilepill.tag = up
            }
        }
        binding.ivPersonalInfoPill.setOnClickListener {
            val intent = Intent(requireContext(), PersonalInformationActivity::class.java)
            startActivity(intent)
        }
        binding.ivChangePasswordPill.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
    }


}