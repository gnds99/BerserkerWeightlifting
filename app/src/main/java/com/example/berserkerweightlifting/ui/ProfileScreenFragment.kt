package com.example.berserkerweightlifting.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.databinding.FragmentProfileScreenBinding
import com.example.berserkerweightlifting.sharedPreferences.UserApplication.Companion.prefs
import com.example.berserkerweightlifting.viewModel.AppViewModel


class ProfileScreenFragment : Fragment() {
    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentProfileScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        Log.d(tag, "Creado onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.customObjects()
        sharedViewModel.user.observe(viewLifecycleOwner){
            val nombre = sharedViewModel.user.value?.name.toString()
            val apellido = sharedViewModel.user.value?.lastname.toString()
            binding.tvUserName.text = "$nombre $apellido"
        }

        // Configuracion del botom para ir a la informacion del usuario
        binding.btnInformacion.setOnClickListener {
            this.goToUserInformation()
        }
        binding.btnMarcas.setOnClickListener {
            this.goToPrInformation()
        }
        binding.btnSalir.setOnClickListener {
            this.logOut()
        }
    }

    // RUTAS
    private fun goToUserInformation(){
        val action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToUserInformationScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToPrInformation(){
        val action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToPrsScreenFragment()
        findNavController().navigate(action)
    }

    private fun logOut(){
        if(sharedViewModel.signOff()){
            prefs.wipe()
            Toast.makeText(context, prefs.getId(), Toast.LENGTH_SHORT).show()
            val action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToLoginScreenFragment()
            findNavController().navigate(R.id.action_profileScreenFragment_to_loginScreenFragment)
        }else{
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }
        /*if(sharedViewModel.signOff()){
            val action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToLoginScreenFragment()
            findNavController().navigate(action)
        }else{
            Toast.makeText(context, "Hubo un error al intentar cerrar sesión", Toast.LENGTH_SHORT).show()
        }*/

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "Mostrando" + savedInstanceState.toString())
        Log.d(tag, "Creado Profile")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tag, "Destruido onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Destruido onDestroy")
        _binding = null
    }

}