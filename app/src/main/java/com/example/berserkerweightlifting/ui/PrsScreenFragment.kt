package com.example.berserkerweightlifting.ui

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.core.message
import com.example.berserkerweightlifting.databinding.FragmentPrsScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel

class PrsScreenFragment : Fragment() {
    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentPrsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }
        sharedViewModel.user.observe(viewLifecycleOwner){
            val user = sharedViewModel.user.value
            binding.txtClean.editText?.setText(user?.clean_jerk.toString())
            binding.txtSnatch.editText?.setText(user?.santch.toString())
            binding.txtPowerClean.editText?.setText(user?.powerClean_jerk.toString())
            binding.txtPowerSnatch.editText?.setText(user?.powerSantc.toString())
            binding.txtBackSquat.editText?.setText(user?.backSquat.toString())
            binding.txtFrontSquat.editText?.setText(user?.frontSquat.toString())
        }
        binding.btnSave.setOnClickListener {
            this.saveChanges()
        }
    }

    // METODOS
    private fun saveChanges(){
        // TODO: Mandar la informacion a la base de datos
        val cleanJerk = clearEntry(binding.txtClean.editText?.text.toString())
        val snatch = clearEntry(binding.txtSnatch.editText?.text.toString())
        val powerCleanJerk = clearEntry(binding.txtPowerClean.editText?.text.toString())
        val powerSnatch = clearEntry(binding.txtPowerSnatch.editText?.text.toString())
        val backSquat = clearEntry(binding.txtBackSquat.editText?.text.toString())
        val FrontSquat = clearEntry(binding.txtFrontSquat.editText?.text.toString())

        sharedViewModel.updateUserPr(cleanJerk,snatch,powerCleanJerk,powerSnatch,backSquat,FrontSquat)
        // Regresando a la pantalla principal
        message("INFORMACIÓN GUARDADA", requireContext())
        this.goToProfile()
    }


    // Rutas
    private fun goToProfile(){
        val action = PrsScreenFragmentDirections.actionPrsScreenFragmentToProfileScreenFragment()
        findNavController().navigate(action)
    }

    private fun clearEntry(text: String): String{
        val text = text.replace("\\s+".toRegex(), "")
        return text
    }



}