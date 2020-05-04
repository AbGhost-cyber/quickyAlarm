package com.example.quicky_alarmapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.quicky_alarmapp.R
import com.example.quicky_alarmapp.adapter.ItemAdapter
import com.example.quicky_alarmapp.data.CustomBottomBar
import com.example.quicky_alarmapp.data.CustomBottomItem
import com.example.quicky_alarmapp.notification.ACTION_STOP_ALARM
import com.example.quicky_alarmapp.receiver.AlarmReceiver

@SuppressLint("ResourceType")
class MainActivity : AppCompatActivity(), ItemAdapter.ItemSelectorInterface {

    private var customBottomBar: CustomBottomBar? = null
   private val ALARM_HOME = 0
   private val SETTINGS = 1
    private var fm: androidx.fragment.app.FragmentManager?=null
    private var active: Fragment? = null
    private var fragment1: Fragment? = null
    private var fragment2: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action=intent!!.action

        //stop alarm sound on notification click
        if(action == ACTION_STOP_ALARM){
            AlarmReceiver.MySounds.taskRingtone!!.stop()
            AlarmReceiver.MySounds.vibrator!!.cancel()

        }

        fragment1 = AlarmFragment()
        fragment2 = SettingsFragment()
        active = fragment1
        fm = supportFragmentManager
        fm!!.beginTransaction().add(R.id.fragmentcontainer, fragment2!!, "2").hide(fragment2!!).commit()
        fm!!.beginTransaction().add(R.id.fragmentcontainer, fragment1!!, "1").commit()
        customBottomBar = CustomBottomBar(
            this,
          findViewById(R.id.customBottomBar),
            this@MainActivity
        )
        initItems()
        customBottomBar!!.changeBackground(getString(R.color.colorItemDefaultBackground))
        customBottomBar!!.defaultBackground = getString(R.color.colorItemDefaultBackground)
        customBottomBar!!.defaultTint = getString(R.color.colorItemDefaultTint)
        customBottomBar!!.changeDividerColor(getString(R.color.colorDivider))
        customBottomBar!!.hideDivider()
        customBottomBar!!.apply(ALARM_HOME)
    }

    @SuppressLint("ResourceType")
    private fun initItems() {
        val alarmHome = CustomBottomItem(
            ALARM_HOME,
            R.drawable.ic_alarm, getString(R.string.Alarms),
            getString(R.color.colorItem1Background), getString(R.color.background_color1)
        )

        val settings = CustomBottomItem(
            SETTINGS, R.drawable.ic_settings,
            getString(R.string.settings), getString(R.color.colorItem2Background),
            getString(R.color.background_color1)
        )


        customBottomBar!!.addItem(alarmHome)
        customBottomBar!!.addItem(settings)
    }

    override fun itemSelect(selectedID: Int) {

        if(selectedID==(ALARM_HOME)){
            fm!!.beginTransaction().hide(active!!).show(fragment1!!).commit()
            active = fragment1
            try {
                fragment1!!.onStart()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        else if(selectedID==(SETTINGS)){
            fm!!.beginTransaction().hide(active!!).show(fragment2!!).commit()
            active = fragment2
            try {
                fragment2!!.onStart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

   }
}
