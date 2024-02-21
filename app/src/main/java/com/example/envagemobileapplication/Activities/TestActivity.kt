package com.example.envagemobileapplication.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.ActivityTest2Binding
import com.leo.searchablespinner.interfaces.OnItemSelectListener


class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTest2Binding
    var isTextInputLayoutClicked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val searchableSpinner = com.leo.searchablespinner.SearchableSpinner(this)
        searchableSpinner.windowTitle = "SEARCHABLE SPINNER"
        searchableSpinner.onItemSelectListener = object : OnItemSelectListener {
            override fun setOnItemSelectListener(position: Int, selectedString: String) {
                Toast.makeText(
                    this@TestActivity,
                    "${searchableSpinner.selectedItem}  ${searchableSpinner.selectedItemPosition}",
                    Toast.LENGTH_SHORT
                ).show()
                if (isTextInputLayoutClicked)
                    binding.textInputSpinner.editText?.setText(selectedString)
                else
                    binding.editTextSpinner.setText(selectedString)
            }
        }

        val androidVersionList = arrayListOf(
            "Cupcake",
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow",
            "Nougat",
            "10"
        )

        searchableSpinner.setSpinnerListItems(androidVersionList)
        binding.textInputSpinner.editText?.keyListener = null
        binding.textInputSpinner.editText?.setOnClickListener {
            isTextInputLayoutClicked = true
            searchableSpinner.show()
        }

        binding.editTextSpinner.keyListener = null
        binding.editTextSpinner.setOnClickListener {
            searchableSpinner.highlightSelectedItem = false
            isTextInputLayoutClicked = false
            searchableSpinner.show()
        }
    }
}