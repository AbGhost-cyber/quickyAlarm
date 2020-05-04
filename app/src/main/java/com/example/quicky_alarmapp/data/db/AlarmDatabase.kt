package com.example.quicky_alarmapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quicky_alarmapp.data.AlarmDao
import com.example.quicky_alarmapp.data.entities.Alarms


@Database(entities = [Alarms::class], version = 5, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun getALarmDao(): AlarmDao

    companion object {
        private var instance: AlarmDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also { instance = it }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            AlarmDatabase::class.java, "alarmDb.db"
        ).fallbackToDestructiveMigration().build()
    }
}

