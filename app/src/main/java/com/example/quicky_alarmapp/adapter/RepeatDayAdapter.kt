package com.example.quicky_alarmapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quicky_alarmapp.R
import com.example.quicky_alarmapp.data.RepeatDays
import com.example.quicky_alarmapp.ui.GetCheckedAlarmDays
import kotlinx.android.synthetic.main.repeat_items.view.*

class RepeatDayAdapter(
    private val repeatList: List<RepeatDays>,
    private val getCheckedAlarmDays: GetCheckedAlarmDays,
    private val context: Context
) :
    RecyclerView.Adapter<RepeatDayAdapter.RepeatViewHolder>() {
    private val checkedRepeatedDays = mutableListOf<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepeatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repeat_items, parent, false)
        return RepeatViewHolder(view)

    }

    override fun getItemCount(): Int {
        return repeatList.size
    }

    override fun onBindViewHolder(holder: RepeatViewHolder, position: Int) {

        val repeatDay = repeatList[position]
        holder.setData(repeatDay, position)

        //get sharedPrefs
        val sharedprefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val checked = sharedprefs.getBoolean("Ischecked $repeatDay", false)

        holder.itemView.checkedDay.isChecked = checked

        //if it is checked, save the state of the list and add the repeated day to the list
        if (checked) {
            val formattedRepeatDay = repeatDay.day.substring(6, 9) + " "
            checkedRepeatedDays.add(formattedRepeatDay)
            getCheckedAlarmDays.getChecked(checkedRepeatedDays)

        }

    }


    inner class RepeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var repeatDays: RepeatDays? = null
        var currentPosition = 0


        init {
            itemView.setOnClickListener {
                if (itemView.checkedDay.isChecked) {
                    itemView.checkedDay.isChecked = false

                    // get the the text of the checked textview to substring 6,9
                    val substring = repeatDays!!.day.substring(6, 9) + " "

                    // if checked day is unchecked, remove it
                    checkedRepeatedDays.remove(substring)

                    getCheckedAlarmDays.getChecked(checkedRepeatedDays)

                    //put boolean value in shared prefs
                    val editor = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                    editor.putBoolean("Ischecked $repeatDays", false).apply()

                } else {
                    itemView.checkedDay.isChecked = true
                    val formattedRepeatDay = repeatDays!!.day.substring(6, 9) + " "

                    // add checked day to list
                    checkedRepeatedDays.add(formattedRepeatDay)
                    getCheckedAlarmDays.getChecked(checkedRepeatedDays)

                    val editor = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                    editor.putBoolean("Ischecked $repeatDays", true).apply()
                }
            }
        }

        fun setData(repeatDays: RepeatDays?, pos: Int) {
            itemView.checkedDay.text = repeatDays!!.day
            this.repeatDays = repeatDays
            this.currentPosition = pos
        }

    }


}