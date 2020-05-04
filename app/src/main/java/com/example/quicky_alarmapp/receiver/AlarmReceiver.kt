package com.example.quicky_alarmapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Vibrator
import com.example.quicky_alarmapp.notification.AlarmNotificationHelper
import com.example.quicky_alarmapp.receiver.AlarmReceiver.MySounds.alert
import com.example.quicky_alarmapp.receiver.AlarmReceiver.MySounds.taskRingtone
import com.example.quicky_alarmapp.receiver.AlarmReceiver.MySounds.vibrator
import java.util.*
@Suppress("DEPRECATION")
class AlarmReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val alarmNotificationHelper=AlarmNotificationHelper(context)
        alarmNotificationHelper.stopAlarmTone(context)
        vibrator=context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if(vibrator!=null){
           vibrator!!.vibrate(3000)
        }
        alert= RingtoneManager.getDefaultUri (RingtoneManager.TYPE_ALARM)
        taskRingtone=RingtoneManager.getRingtone (context,alert)
        if(taskRingtone!=null){
            taskRingtone!!.play ()
        }
        val notification=alarmNotificationHelper.getNotificationBuilder().build()
        alarmNotificationHelper.getManager().notify(getID(),notification)
    }



    private fun getID(): Int {
        return (Date().time / 1000L % Int.MAX_VALUE).toInt()
    }

    object MySounds{
        var taskRingtone: Ringtone? = null
        var alert: Uri? = null
        var vibrator: Vibrator? = null
    }


}