package com.example.berserkerweightlifting.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.berserkerweightlifting.core.Options
import com.example.berserkerweightlifting.data.models.ValidateEmail
import com.example.berserkerweightlifting.databinding.FragmentResetScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel


class ResetScreenFragment : Fragment() {
    private val sharedViewModel: AppViewModel by activityViewModels()
    var _binding: FragmentResetScreenBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentResetScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        sharedViewModel.reset.observe(viewLifecycleOwner){
            if(sharedViewModel.reset.value == Options.RESETED){
                Toast.makeText(context, "Verifica tu email", Toast.LENGTH_SHORT).show()
            }else if(sharedViewModel.reset.value == Options.NoRESETED){
                Toast.makeText(context, "Hubo un error", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRecuperar.setOnClickListener {
            val email = clearEntry(binding.txtCorreoUsuario.editText?.text.toString())
            val validEmail = validateEmail(email)
            if(validEmail){
                sharedViewModel.resetPassword(email)
                binding.txtCorreoUsuario.editText?.setText("")
            }
        }
    }

    private fun clearEntry(text: String): String{
        val text = text.replace("\\s+".toRegex(), "")
        return text
    }
    private fun validateEmail(email: String): Boolean{

        if( !ValidateEmail.isEmail(email)){
            binding.txtCorreoUsuario.error = "Correo no valido"
            return false
        }else{
            binding.txtCorreoUsuario.error = ""
            return true
        }
    }


}