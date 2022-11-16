package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.data.models.TimeSlot
import com.example.berserkerweightlifting.databinding.FragmentHomeScreenBinding
import com.example.berserkerweightlifting.recyclerView.adapter.DateAdapter
import com.example.berserkerweightlifting.viewModel.AppViewModel


class HomeScreenFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private  var periodOfTime: MutableList<TimeSlot> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val diasPaquete = sharedViewModel.user.value!!.paquete.toInt()


        for (i in 1..7) {

            periodOfTime.add(TimeSlot(i.toString(), "Sem 1"))
        }

        val recyclerView = binding.calendar
       recyclerView.layoutManager = GridLayoutManager(context, 3)
        //recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false )
        recyclerView.adapter = DateAdapter(periodOfTime)
    }

    private fun logOut(){
        findNavController().navigate(R.id.loginScreenFragment)
    }

}