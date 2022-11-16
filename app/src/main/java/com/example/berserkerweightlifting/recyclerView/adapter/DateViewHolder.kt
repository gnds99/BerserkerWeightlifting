package com.example.berserkerweightlifting.recyclerView.adapter

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.data.models.TimeSlot

class DateViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val date = view.findViewById<TextView>(R.id.date_no_focus)
    val week = view.findViewById<TextView>(R.id.textView2)
    val card = view.findViewById<CardView>(R.id.date_card)

    fun render(timeSlot: TimeSlot){
        date.text = timeSlot.day
        week.text = timeSlot.week
        card.setOnClickListener {
            this.goToRoutine()

        }
    }

    fun goToRoutine(){
        view.findNavController().navigate(R.id.rutinaScreenFragment)
    }


}