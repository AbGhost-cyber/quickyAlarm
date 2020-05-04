package com.example.quicky_alarmapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quicky_alarmapp.R
import com.example.quicky_alarmapp.adapter.RepeatDayAdapter
import com.example.quicky_alarmapp.data.DaySupplier
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottomsheet_dialog_layout.*
import kotlinx.android.synthetic.main.repeat_layout.*

class RepeatDaysBottomSheetDialog(context: Context, var getCheckedAlarmDays: GetCheckedAlarmDays) :
    BottomSheetDialog(context, R.style.SheetDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repeat_layout)
        setCancelable(false)

        val layoutManager= LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL
        repeatRecView.layoutManager=layoutManager
        val adapter=RepeatDayAdapter(DaySupplier.days,getCheckedAlarmDays,context)
        repeatRecView.adapter=adapter


        back_bottomsheet.setOnClickListener {
            dismiss()
        }



    }



}
