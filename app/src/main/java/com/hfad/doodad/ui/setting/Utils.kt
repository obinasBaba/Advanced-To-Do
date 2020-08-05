package com.hfad.doodad.ui.setting

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerVoo(private  var block: TimePickerDialog.OnTimeSetListener ) : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        return TimePickerDialog(requireContext(), block, c.get( Calendar.HOUR_OF_DAY ), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(requireContext()) )
    }
}