package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.AddGuestFragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.GuestActivity
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.AddGuestFragmentsDataListener
import com.example.envagemobileapplication.databinding.FragmentAddGuestDescriptionBinding
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddGuestDescriptionF : Fragment() {
    private var descriptionfinal: MultipartBody.Part? = null
    private var mEditor: RichEditor? = null

    var global = com.example.envagemobileapplication.Utils.Global
    private var dataListener: AddGuestFragmentsDataListener? = null
    lateinit var binding: FragmentAddGuestDescriptionBinding
    private var descriptiontextforbackpress: String? = ""
    var descriptiontext: String = ""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddGuestFragmentsDataListener) {
            dataListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddGuestDescriptionBinding.inflate(inflater, container, false)
        GuestActivity.binding.btnsave.visibility = View.VISIBLE
        GuestActivity.binding.ivCancel.visibility = View.VISIBLE

        binding.etGuestDescription!!.setOnTextChangeListener { text ->

            descriptiontextforbackpress = text
            if (text.contains("<"))
            {
                descriptiontext = text
                global.descriptiontextguest = descriptiontext
            }
            else {
                descriptiontext = "<p>" + text + "</p>"
                global.descriptiontextguest = descriptiontext
            }



        }

        setUpRtf()
        return binding.root
    }

    override fun onResume() {

        /* if (global.guestData.description != null) {
             binding.etClientDescription.setText(global.guestData.description.toString())
         }*/

        super.onResume()
    }

    override fun onPause() {
        GuestActivity.binding.btnsave.visibility = View.GONE
        GuestActivity.binding.ivCancel.visibility = View.GONE
        val description = descriptiontext


        // Call the interface method with the data
        dataListener?.onDataReceived(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            description
        )
        super.onPause()
    }

    private fun setUpRtf() {
        mEditor = binding.etGuestDescription
        mEditor!!.setEditorHeight(110)
        mEditor!!.setEditorFontSize(14)
        mEditor!!.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor!!.setPadding(0, 10, 10, 10)
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor!!.setPlaceholder("Description")
        //mEditor.setInputEnabled(false);
        //  mPreview = findViewById<View>(R.id.preview) as TextView
        //   mEditor!!.setOnTextChangeListener { text -> mPreview!!.text = text }
        binding.actionUndo.setOnClickListener { binding.etGuestDescription!!.undo() }

        binding.actionRedo.setOnClickListener { binding.etGuestDescription!!.redo() }

        binding.actionBold.setOnClickListener { binding.etGuestDescription!!.setBold() }

        binding.actionItalic.setOnClickListener { binding.etGuestDescription!!.setItalic() }

        binding.actionStrikethrough.setOnClickListener { binding.etGuestDescription!!.setStrikeThrough() }

        binding.actionUnderline.setOnClickListener { binding.etGuestDescription!!.setUnderline() }

        binding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                binding.etGuestDescription!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionBgColor.setOnClickListener(
            object : View.OnClickListener {
                private var isChanged = false
                override fun onClick(v: View) {
                    binding.etGuestDescription!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                    isChanged = !isChanged
                }
            })

        binding.actionAlignLeft.setOnClickListener { binding.etGuestDescription!!.setAlignLeft() }

        binding.actionAlignCenter.setOnClickListener { binding.etGuestDescription!!.setAlignCenter() }

        binding.actionAlignRight.setOnClickListener { binding.etGuestDescription!!.setAlignRight() }


        binding.actionInsertBullets.setOnClickListener { binding.etGuestDescription!!.setBullets() }

        binding.actionInsertNumbers.setOnClickListener { binding.etGuestDescription!!.setNumbers() }

        binding.actionInsertLink.setOnClickListener {
            binding.etGuestDescription!!.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
    }

}