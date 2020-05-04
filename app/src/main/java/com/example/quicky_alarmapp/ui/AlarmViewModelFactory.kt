package com.example.quicky_alarmapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quicky_alarmapp.data.repositories.AlarmRepository

@Suppress("UNCHECKED_CAST")
class AlarmViewModelFactory(private val repository: AlarmRepository):
    ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlarmViewModel(repository) as T
    }
}