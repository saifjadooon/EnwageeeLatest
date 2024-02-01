package com.example.envagemobileapplication.Activities.DashBoard.SettingsDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.envagemobileapplication.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}