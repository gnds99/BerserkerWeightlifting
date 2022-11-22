package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.berserkerweightlifting.databinding.FragmentPremiumScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel


class PremiumScreenFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentPremiumScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPremiumScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.anual.setOnClickListener {
            Toast.makeText(context, "Opción no disponible", Toast.LENGTH_SHORT).show()
        }
        binding.mensual.setOnClickListener {
            Toast.makeText(context, "Opción no disponible", Toast.LENGTH_SHORT).show()
        }
        binding.trimestral.setOnClickListener {
            Toast.makeText(context, "Opción no disponible", Toast.LENGTH_SHORT).show()
        }

        binding.semestral.setOnClickListener {
            Toast.makeText(context, "Opción no disponible", Toast.LENGTH_SHORT).show()
        }
        binding.btnPrueba.setOnClickListener {
            if(!sharedViewModel.user.value!!.pruebaGratis){
                sharedViewModel.iniciarPruebaGratis()
                Toast.makeText(context, "Haz iniciado tu prueba gratis", Toast.LENGTH_SHORT).show()
                sharedViewModel.preimum = true
            }else{
                Toast.makeText(context, "Ya obtuviste tu prueba gratis", Toast.LENGTH_SHORT).show()
            }
        }

    }






}