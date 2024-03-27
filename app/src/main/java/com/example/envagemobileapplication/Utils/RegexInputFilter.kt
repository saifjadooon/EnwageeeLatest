package com.example.envagemobileapplication.Utils

import android.text.InputFilter
import android.text.Spanned


class RegexInputFilter(private val regex: String) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val input = dest.toString().substring(0, dstart) +
                source.toString().substring(start, end) +
                dest.toString().substring(dend)

        return if (Regex(regex).matches(input)) {
            null // Accept the input
        } else {
            ""   // Reject the input
        }
    }
}