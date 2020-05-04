package com.example.quicky_alarmapp.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import com.example.quicky_alarmapp.R
import com.example.quicky_alarmapp.data.entities.Alarms
import com.example.quicky_alarmapp.receiver.AlarmReceiver
import com.example.quicky_alarmapp.ui.AlarmFragment.Toast.displayToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottomsheet_dialog_layout.*
import java.util.*

@Suppress("IMPLICIT_CAST_TO_ANY", "DEPRECATION")
class AlarmBottomSheetDialog(
    context: Context,
    var addAlarmListener:
    AddAlarmListener,
    var calendar: myCalendar
) : BottomSheetDialog(context, R.style.SheetDialog) {
    lateinit var tp: TimePicker
    lateinit var AM_PM: String
    var hour = 0
    var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottomsheet_dialog_layout)
        setCancelable(false)


        //clear sharedprefs of repeatDaysAdapter if any(on every new view created)
        val settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        settings.edit().clear().apply()

        tp = findViewById(R.id.timePicker)!!
        Ok_bottomsheet.setOnClickListener {
            val time = findViewById<TextView>(R.id.alarm_time)
            //calendar = Calendar.getInstance()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.calendar.set(Calendar.HOUR_OF_DAY, tp.hour)
                calendar.calendar.set(Calendar.MINUTE, tp.minute)
                calendar.calendar.set(Calendar.SECOND, 0)
                calendar.calendar.set(Calendar.MILLISECOND, 0)


                hour = tp.hour
                minute = tp.minute
            } else {
                calendar.calendar.set(Calendar.HOUR_OF_DAY, tp.currentHour)
                calendar.calendar.set(Calendar.MINUTE, tp.currentMinute)
                calendar.calendar.set(Calendar.SECOND, 0)
                calendar.calendar.set(Calendar.MILLISECOND, 0)

                hour = tp.currentHour
                minute = tp.currentMinute
            }

            var hourString = hour.toString()
            var minString = minute.toString()

            //checks if system time isn't in 24 hours format
            if (!android.text.format.DateFormat.is24HourFormat(context)) {
                if (hour > 12) {
                    hourString = (hour - 12).toString()
                    AM_PM = "PM"
                } else {
                    AM_PM = "AM"
                }

                if (minute < 10) {
                    minString = "0$minute"
                }
                val formattedTime = "$hourString:$minString $AM_PM"

                time!!.text = formattedTime
            } else {
                if (hour > 12) {
                    hourString = (hour - 12).toString()

                }
                if (minute < 10) {
                    minString = "0$minute"
                }
                val formattedTime = "$hourString:$minString"

                time!!.text = formattedTime
            }
            if (repeat_items.text == "Never" || repeat_items.text.isEmpty()) {
                repeat_items.text = "Alarm"
            }

            val alarm = Alarms(time.text.toString(), repeat_items.text.toString(), true)
            addAlarmListener.addButtonClickListener(alarm)

            //start alarm based on time set
          // startAlarm(calendar, repeat_items.text.toString(), hour, minute,alarm.id)

            dismiss()
        }


        cancel_bottomsheet.setOnClickListener {
            displayToast(context, "cancelled")
            cancel()
        }



        repeat.setOnClickListener {
            RepeatDaysBottomSheetDialog(context, object : GetCheckedAlarmDays {
                override fun getChecked(name: MutableList<String>) {
                    //append days received from repeat days buttomsheet
                    val builder = StringBuilder()
                    for (days in name) {
                        builder.append(days + "")
                    }

                    when (name.size) {

                        //checks if all repeatedday is selected
                        7 -> repeat_items.text = "Every Day"

                        //checks if only one is selected
                        1 -> {
                            var text: String? = null
                            when {
                                builder.toString().equals("sun ", true) -> text = "Every Sunday"
                                builder.toString().equals("mon ", true) -> text = "Every Monday"
                                builder.toString().equals("tue ", true) -> text = "Every Tuesday"
                                builder.toString().equals("wed ", true) -> text = "Every Wednesday"
                                builder.toString().equals("thu ", true) -> text = "Every Thursday"
                                builder.toString().equals("fri ", true) -> text = "Every Friday"
                                builder.toString().equals("sat ", true) -> text = "Every Saturday"
                            }
                            repeat_items.text = text
                        }
                        else -> repeat_items.text = builder.toString()
                    }
                    //checks if no repeated Day was selected
                    if (repeat_items.text.isEmpty()) {
                        repeat_items.text = "Never"
                    }
                }
            }).show()
        }
    }


object myCalendar{
    var calendar=Calendar.getInstance()
}

//    private fun startAlarm(calendar: Calendar, repeatDay: String, hour: Int, min: Int,id:Int) {
//        var weekday: Int? = null
//      //  val id = System.currentTimeMillis().toInt()
//
//        //this gets executed if mon,tue,wed,thu,fri or sat is selected independently or mixed
//        if (repeatDay.contains("Sun", true) || repeatDay.contains("Every Sunday")) {
//            weekday = Calendar.SUNDAY
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        if (repeatDay.contains("Mon", true) || repeatDay.contains("Every Monday")) {
//            weekday = Calendar.MONDAY
//
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        if (repeatDay.contains("Tue", true) || repeatDay.contains("Every Tuesday")) {
//            weekday = Calendar.TUESDAY
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        if (repeatDay.contains("Wed", true) || repeatDay.contains("Every Wednesday")) {
//            weekday = Calendar.WEDNESDAY
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        if (repeatDay.contains("Thu", true) || repeatDay.contains("Every Thursday")) {
//            weekday = Calendar.THURSDAY
//
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        if (repeatDay.contains("Fri", true) || repeatDay.contains("Every Wednesday")) {
//            weekday = Calendar.FRIDAY
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        if (repeatDay.contains("Sat", true) || repeatDay.contains("Every Saturday")) {
//            weekday = Calendar.SATURDAY
//            calendar.set(Calendar.DAY_OF_WEEK, weekday)
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, min)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            val now = Calendar.getInstance()
//            now.set(Calendar.SECOND, 0)
//            now.set(Calendar.MILLISECOND, 0)
//            if (calendar.before(now)) {
//                calendar.add(Calendar.DATE, 7)
//            }
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                7 * 24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//        //this only gets executed if no day is selected
//        if (repeatDay.contains("Never", true) || repeatDay.contains("Alarm")) {
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//        }
//        //this gets executed when all days are selected
//        if (repeatDay.contains("Every Day", true)) {
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent =
//                PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                24 * 60 * 60 * 1000,
//                pendingIntent
//            )
//        }
//    }


}


