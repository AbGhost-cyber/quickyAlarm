package com.example.quicky_alarmapp.ui

import com.example.quicky_alarmapp.data.entities.Alarms

interface AddAlarmListener {
    fun addButtonClickListener(alarm:Alarms){

    }
}

interface GetCheckedAlarmDays{
    fun getChecked(name:MutableList<String>){
    }
}