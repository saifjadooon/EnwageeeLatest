package com.example.envagemobileapplication.Activities.Assesments.AssesmentFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.envagemobileapplication.Models.RequestModels.Section
import com.example.envagemobileapplication.R

class AssessmentFragment : Fragment() {

    companion object {
        private const val ARG_SECTION = "section"

        fun newInstance(section: Section): AssessmentFragment {
            val fragment = AssessmentFragment()
            val args = Bundle()
            args.putParcelable(ARG_SECTION, section)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var section: Section

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            section = it.getParcelable(ARG_SECTION)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_assessment, container, false)

        // Customize the layout based on the section data
        val sectionNameTextView: TextView = rootView.findViewById(R.id.sectionNameTextView)
        sectionNameTextView.text = section.name

        // Add other UI customization logic here

        return rootView
    }
}