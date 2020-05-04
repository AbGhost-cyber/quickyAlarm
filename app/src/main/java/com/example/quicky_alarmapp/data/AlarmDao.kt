package com.example.quicky_alarmapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quicky_alarmapp.data.entities.Alarms

@Dao
interface AlarmDao {

    @Insert
    suspend fun insert(alarm:Alarms)

    @Update
    suspend fun update(alarm:Alarms)

    @Delete
    suspend fun delete(alarm: Alarms)

    @Query("DELETE FROM alarms_items")
    suspend fun deleteAllAlarms()

    @Query("SELECT * FROM alarms_items")
    fun getAllAlarms():LiveData<List<Alarms>>
}