package com.example.quicky_alarmapp.notification

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.quicky_alarmapp.R.drawable.*
import com.example.quicky_alarmapp.receiver.AlarmReceiver
import com.example.quicky_alarmapp.service.Alarmservice
import com.example.quicky_alarmapp.ui.MainActivity
const val ACTION_STOP_ALARM ="ACTION_STOP_ALARM"
const val ACTION_SNOOZE_ALARM ="ACTION_SNOOZE_ALARM"
class AlarmNotificationHelper(base:Context) :ContextWrapper(base) {
   private val notificationId = System.currentTimeMillis().toInt()
    private val MYCHANNEL_ID= "Alarm Notification ID"
    private val MYCHANNEL_NAME= "Alarm Alarm"


    private var manager:NotificationManager?=null

    init {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannels()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels(){
        val channel=NotificationChannel(
            MYCHANNEL_ID,
            MYCHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW)
        channel.enableVibration(true)
        getManager().createNotificationChannel(channel)
    }

    fun getManager():NotificationManager{
        if(manager==null) manager=
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return manager as NotificationManager
    }

    fun getNotificationBuilder():NotificationCompat.Builder{
        val intent=Intent(this,MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
            action= ACTION_STOP_ALARM
        }
        val pendingIntent=PendingIntent.getActivity(this,notificationId,intent,PendingIntent.FLAG_ONE_SHOT)

        return NotificationCompat.Builder(applicationContext,MYCHANNEL_ID)
            .setContentText("Alarm")
            .setSmallIcon(ic_alarm)
            .setColor(Color.YELLOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            . addAction(ic_alarm_stop,"Stop",stopAlarmTone(this))
            .addAction(ic_snooze,"Snooze",snoozeAlarm(this))



    }
    fun stopAlarmTone(context:Context):PendingIntent{
        val stopAlarmIntent=Intent(context,Alarmservice::class.java).apply {
            action= ACTION_STOP_ALARM
        }
        return PendingIntent.getService(this, notificationId,stopAlarmIntent,PendingIntent.FLAG_UPDATE_CURRENT)

    }

    private fun snoozeAlarm(context:Context):PendingIntent{
       val snoozeIntent=Intent(context,Alarmservice::class.java).apply {
           action= ACTION_SNOOZE_ALARM
       }
        return PendingIntent.getService(this, notificationId,snoozeIntent,PendingIntent.FLAG_UPDATE_CURRENT)
    }




}