package com.example.envagemobileapplication.Activities.ForgotPasword.ForgotPasswordFragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ForGotPasswordSharedViewModel
import com.example.envagemobileapplication.databinding.FragmentEnterEmailBinding

class EnterEmailF : Fragment() {
    private var email: String = ""
    lateinit var binding: FragmentEnterEmailBinding
    lateinit var tokenManager: TokenManager
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var loader: Loader
    val sharedViewModel: ForGotPasswordSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterEmailBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        observers()
        return binding.root

    }

    private fun observers() {
        sharedViewModel.LDSendEmail.observe(requireActivity()) {
            loader.hide()

            if (it.data != null) {
                if (it.data.status.equals(true)) {
                    val toast = Toast.makeText(
                        requireContext(),
                        "Reset Password Verification code has been sent to your email.",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    replaceFragment(VerifyOtpF())
                }
            }
        }
    }

    private fun initViews() {
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())

    }

    private fun clickListeners() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {


            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    binding.TIEmail.error = null
                }
                else {
                    val validatedEmail: String? =
                        validateEmail(editable.toString(), binding.etEmail)

                    if (validatedEmail != null) {
                        // Use the validated email
                        email = validatedEmail

                    } else {
                        email = ""
                        // Handle the case where the email is invalid
                    }
                }

            }
        })
        binding.sendotp.setOnClickListener {

            if (email != "") {
                loader.show()
                global.emailforgotpassword = email
                sharedViewModel.sendEmail(email, requireContext(),loader,binding)
            } else {

                binding.TIEmail.error = "Email is Required"
                binding.TIEmail.setErrorIconDrawable(null)// Set the error message
                binding.TIEmail.setErrorTextAppearance(R.style.ErrorText);
            }


        }
        binding.
        ivback.setOnClickListener{
            showDialog()
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

    fun validateEmail(email: String, etBCC: EditText): String? {
        return if (!Global.emailRegex.matches(email)) {

            binding.TIEmail.error = "Invalid email address"
            binding.TIEmail.errorIconDrawable = null// Set the error message
            binding.TIEmail.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
            null
        } else {
            binding.TIEmail.error = null
            email
        }
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            requireActivity().finishAffinity()
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