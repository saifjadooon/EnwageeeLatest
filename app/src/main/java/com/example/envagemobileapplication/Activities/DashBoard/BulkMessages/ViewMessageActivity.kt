package com.example.envagemobileapplication.Activities.DashBoard.BulkMessages

import BaseActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.envagemobileapplication.databinding.ActivityViewMessageBinding

class ViewMessageActivity : BaseActivity() {
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: ActivityViewMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityViewMessageBinding.inflate(layoutInflater)
        try {
            binding.tvMsg.text = global.message
            binding.tvEmailFrom.text = global.from
            binding.tvEmailTo.text = global.too
        } catch (e: Exception) {
        }
        binding.ivcCross.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }
}