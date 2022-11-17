package com.example.berserkerweightlifting.recyclerView.adapter

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.data.models.TimeSlot
import com.example.berserkerweightlifting.ui.HomeScreenFragmentDirections

class DateViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val date = view.findViewById<TextView>(R.id.date_no_focus)
    val week = view.findViewById<TextView>(R.id.textView2)
    val card = view.findViewById<CardView>(R.id.date_card)

    fun render(timeSlot: TimeSlot){
        date.text = timeSlot.day
        week.text = timeSlot.week
        val day = timeSlot.day.toInt()
        card.setOnClickListener {
            this.goToRoutine(day)

        }
    }

    fun goToRoutine(time: Int){
        val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToRutinaScreenFragment(day = time)
        view.findNavController().navigate(action)
    }


}