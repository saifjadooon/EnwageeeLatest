package com.example.envagemobileapplication.Utils

import android.text.InputFilter
import android.text.Spanned


class PhoneNumberFormatter(private val format: String) : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        // Remove any dashes from the existing text
        val unformatted = dest.toString().replace("-".toRegex(), "")

        // Apply the format to the text
        val formatted = StringBuilder()
        var index = 0
        var i = 0
        while (i < format.length && index < unformatted.length) {
            if (format[i] == '#') {
                formatted.append(unformatted[index])
                index++
            } else {
                formatted.append(format[i])
            }
            i++
        }
        return formatted.toString()
    }
}