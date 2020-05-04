package com.example.quicky_alarmapp.adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quicky_alarmapp.R
import com.example.quicky_alarmapp.data.entities.Alarms
import com.example.quicky_alarmapp.receiver.AlarmReceiver
import com.example.quicky_alarmapp.ui.AlarmFragment
import com.example.quicky_alarmapp.ui.AlarmViewModel
import kotlinx.android.synthetic.main.alarm_items.view.*
import java.util.*

class AlarmAdapter(
    private val alarmViewModel: AlarmViewModel,
    var alarmList: List<Alarms>,
    val calendar: Calendar
) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_items, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int {

        return alarmList.size
    }
    fun getAlarmAt(position: Int):Alarms {
        return alarmList[position]
    }
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val currentAlarm = alarmList[position]
        holder.itemView.time_tv.text = currentAlarm.time
        holder.itemView.days_tv.text = currentAlarm.repeatDays

        //this basically checks the state of the switch
        holder.itemView.isActive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                    currentAlarm.AlarmIsEnabled=true
                // if the current time is before the time set.. then start the alarm
                if(Calendar.getInstance().before(calendar)){
                    startAlarm(calendar,currentAlarm.repeatDays,calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),currentAlarm.id,holder.itemView.context)
                }
               alarmViewModel.update(currentAlarm)
            } else {
                currentAlarm.AlarmIsEnabled=false
                cancelAlarm(currentAlarm.id,holder.itemView.context)
                alarmViewModel.update(currentAlarm)
            }
        }
        holder.itemView.isActive.isChecked = currentAlarm.AlarmIsEnabled


    }
    //cancel alarm method
   fun cancelAlarm(id: Int,context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }
    private fun startAlarm(calendar: Calendar, repeatDay: String, hour: Int, min: Int, id:Int, context:Context) {
        var weekday: Int? = null

        //this gets executed if mon,tue,wed,thu,fri or sat is selected independently or mixed
        if (repeatDay.contains("Sun", true) || repeatDay.contains("Every Sunday")) {
            weekday = Calendar.SUNDAY
            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        if (repeatDay.contains("Mon", true) || repeatDay.contains("Every Monday")) {
            weekday = Calendar.MONDAY

            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)

            //this checks if the time is before the current time
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        if (repeatDay.contains("Tue", true) || repeatDay.contains("Every Tuesday")) {
            weekday = Calendar.TUESDAY
            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        if (repeatDay.contains("Wed", true) || repeatDay.contains("Every Wednesday")) {
            weekday = Calendar.WEDNESDAY
            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        if (repeatDay.contains("Thu", true) || repeatDay.contains("Every Thursday")) {
            weekday = Calendar.THURSDAY

            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        if (repeatDay.contains("Fri", true) || repeatDay.contains("Every Wednesday")) {
            weekday = Calendar.FRIDAY
            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        if (repeatDay.contains("Sat", true) || repeatDay.contains("Every Saturday")) {
            weekday = Calendar.SATURDAY
            calendar.set(Calendar.DAY_OF_WEEK, weekday)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val now = Calendar.getInstance()
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7)
            }
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7 * 24 * 60 * 60 * 1000,
                pendingIntent
            )
        }
        //this only gets executed if no day is selected
        if (repeatDay.contains("Never", true) || repeatDay.contains("Alarm")) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
        //this gets executed when all days are selected
        if (repeatDay.contains("Every Day", true)) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DATE, 1)
            }
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }


    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}