package com.example.berserkerweightlifting.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.databinding.FragmentUserInformationScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel


class UserInformationScreenFragment : Fragment() {
    private val sharedViewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentUserInformationScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentUserInformationScreenBinding.inflate(inflater, container, false)
        //sharedViewModel.getUserInformation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.user.observe(viewLifecycleOwner){
            val user = sharedViewModel.user.value
            binding.txtNombreUsuario.editText?.setText(user?.name.toString())
            binding.txtTelefono.editText?.setText(user?.phone.toString())
            binding.txtEdad.editText?.setText(user?.age.toString())
            binding.txtSexo.editText?.setText(user?.gender.toString())
            binding.txtPeso.editText?.setText(user?.weight.toString())
            binding.txtEstatura.editText?.setText(user?.height.toString())
        }
        binding.btnSave.setOnClickListener {
            this.saveChanges()
        }

    }

    // METODOS
    private fun saveChanges(){
        // TODO: Mandar la informacion a la base de datos
        val name = binding.txtNombreUsuario.editText?.text.toString()
        val phone = clearEntry(binding.txtTelefono.editText?.text.toString())
        val age = clearEntry(binding.txtEdad.editText?.text.toString())
        val gender = clearEntry(binding.txtSexo.editText?.text.toString())
        val weight = clearEntry(binding.txtPeso.editText?.text.toString())
        val height = clearEntry(binding.txtEstatura.editText?.text.toString())

        sharedViewModel.updateUserInformation(name,phone,age,gender,weight,height)
        this.goToProfile()
    }



    // Rutas
    private fun goToProfile(){
        val action = UserInformationScreenFragmentDirections.actionUserInformationScreenFragmentToProfileScreenFragment()
        findNavController().navigate(action)
    }

    private fun clearEntry(text: String): String{
        val text = text.replace("\\s+".toRegex(), "")
        return text
    }

}