package com.example.berserkerweightlifting.recyclerView.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.data.models.TimeSlot

class DateAdapter(private val periodOfTime:List<TimeSlot>): RecyclerView.Adapter<DateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DateViewHolder(layoutInflater.inflate(R.layout.card_date, parent, false))

    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val item = periodOfTime[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return periodOfTime.size
    }
}