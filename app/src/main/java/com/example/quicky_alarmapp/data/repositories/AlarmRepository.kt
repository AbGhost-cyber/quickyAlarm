package com.example.quicky_alarmapp.data.repositories

import com.example.quicky_alarmapp.data.db.AlarmDatabase
import com.example.quicky_alarmapp.data.entities.Alarms

class AlarmRepository(private val db: AlarmDatabase) {
    suspend fun insert(alarm: Alarms) = db.getALarmDao().insert(alarm)
    suspend fun update(alarm: Alarms) = db.getALarmDao().update(alarm)
    suspend fun delete(alarm: Alarms) = db.getALarmDao().delete(alarm)
    suspend fun deleteAllAlarms() = db.getALarmDao().deleteAllAlarms()
    fun getAllAlarms() = db.getALarmDao().getAllAlarms()
}