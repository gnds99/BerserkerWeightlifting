package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.databinding.FragmentSettingsScreenBinding

class SettingsScreenFragment : Fragment() {
    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlan.setOnClickListener {
            this.goToPremium()
        }

        binding.btnAyuda.setOnClickListener {
            this.goToHelp()
        }

        binding.btnIdiomas.setOnClickListener {
            this.goToIdiomas()
        }

        binding.btnPreguntas.setOnClickListener {
            this.goToPreguntas()
        }

    }

    // Rutas
    private fun goToPremium(){
        val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToPremiumScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToHelp(){
        val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToAyudaScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToIdiomas(){
        val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToIdiomasScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToPreguntas(){
        val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToPreguntasScreenFragment()
        findNavController().navigate(action)
    }

}