package com.example.quicky_alarmapp.ui

import androidx.lifecycle.ViewModel
import com.example.quicky_alarmapp.data.entities.Alarms
import com.example.quicky_alarmapp.data.repositories.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(private val repos:AlarmRepository) :ViewModel(){

    fun insert(alarm: Alarms)= CoroutineScope(Dispatchers.Main).launch {
        repos.insert(alarm)
    }
    fun update(alarm: Alarms)= CoroutineScope(Dispatchers.Main).launch {
        repos.update(alarm)
    }
    fun delete(alarm: Alarms)= CoroutineScope(Dispatchers.Main).launch {
        repos.delete(alarm)
    }
    fun deleteAll()= CoroutineScope(Dispatchers.Main).launch {
        repos.deleteAllAlarms()
    }

    fun getAllAlarms()=repos.getAllAlarms()
}