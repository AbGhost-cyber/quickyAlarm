package com.example.quicky_alarmapp.data

data class RepeatDays(var day:String)


object DaySupplier{
    val days= listOf(
        RepeatDays("Every Sunday"),
        RepeatDays("Every Monday"),
        RepeatDays("Every Tuesday"),
        RepeatDays("Every Wednesday"),
        RepeatDays("Every Thursday"),
        RepeatDays("Every Friday"),
        RepeatDays("Every Saturday"))
}
