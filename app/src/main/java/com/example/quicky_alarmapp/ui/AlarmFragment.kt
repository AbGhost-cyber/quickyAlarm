package com.example.quicky_alarmapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quicky_alarmapp.R
import com.example.quicky_alarmapp.adapter.AlarmAdapter
import com.example.quicky_alarmapp.data.entities.Alarms
import com.example.quicky_alarmapp.receiver.AlarmReceiver
import com.example.quicky_alarmapp.ui.AlarmBottomSheetDialog.myCalendar.calendar
import com.example.quicky_alarmapp.ui.AlarmFragment.Toast.displayToast
import com.google.android.material.snackbar.Snackbar
import com.muddzdev.styleabletoastlibrary.StyleableToast
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_alarm.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

@Suppress("DEPRECATION")
class AlarmFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: AlarmViewModelFactory by instance()
    private var viewModel: AlarmViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //this tells the fragment hey, we've got a menu item
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)

        viewModel = ViewModelProvider(this, factory).get(AlarmViewModel::class.java)

        val adapter = AlarmAdapter(viewModel!!, listOf(),calendar)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel!!.getAllAlarms().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                emptyRecView.visibility=View.VISIBLE
            }
            adapter.alarmList = it
            adapter.notifyDataSetChanged()
        })


        //swipe delete function
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                if (direction == ItemTouchHelper.LEFT) {
                    val adapterPosition = viewHolder.adapterPosition
                    //get item adapter position
                    val deletedAlarm = adapter.getAlarmAt(adapterPosition)
                    //delete it from the view model
                    viewModel!!.delete(deletedAlarm)
                    //set it's alarm to false
                     deletedAlarm.AlarmIsEnabled=false
                    //cancel its alarm
                    adapter.cancelAlarm(deletedAlarm.id,context!!)
                    adapter.notifyItemRemoved(adapterPosition)


                    Snackbar.make(recyclerView, "Alarm deleted", Snackbar.LENGTH_LONG)
                        .setActionTextColor(resources.getColor(R.color.colorAccent1))
                        .setAction("Undo") { v1 ->
                            viewModel!!.insert(deletedAlarm)
                            adapter.notifyDataSetChanged()
                            //  EndOfPage.setVisibility(View.VISIBLE)
                            adapter.notifyItemInserted(adapterPosition)
                        }
                        .show()
                    return
                }

               // val taskHolder = viewHolder as AlarmAdapter.AlarmViewHolder


            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            Objects.requireNonNull<FragmentActivity>(
                                activity
                            ), R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeLeftLabel("delete")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(recyclerView)
        return view

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_new_alarm) {
            AlarmBottomSheetDialog(requireContext(),
                object : AddAlarmListener {
                    override fun addButtonClickListener(alarm: Alarms) {
                        viewModel!!.insert(alarm)
                        displayToast(context!!, "Alarm created")

                    }
                },AlarmBottomSheetDialog.myCalendar).show()

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.Alarms)
        super.onStart()
    }

    object Toast {
        fun displayToast(context: Context, message: String) {
            StyleableToast.makeText(context, message, R.style.myToast).show()
        }
    }
}





