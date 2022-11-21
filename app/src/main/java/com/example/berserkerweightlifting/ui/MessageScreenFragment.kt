package com.example.berserkerweightlifting.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.berserkerweightlifting.R
import com.example.berserkerweightlifting.databinding.ActivityMainBinding.inflate
import com.example.berserkerweightlifting.databinding.AppBarBinding.inflate
import com.example.berserkerweightlifting.databinding.FragmentMessageScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel

class MessageScreenFragment(): DialogFragment() {


    private lateinit var  binding: FragmentMessageScreenBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FragmentMessageScreenBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.btnPost.setOnClickListener {
            //Toast.makeText(context, "Haz hecho click", Toast.LENGTH_SHORT).show()
            this.goPremium()
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


    private fun goPremium(){
        findNavController().navigate(R.id.premiumScreenFragment)
    }

}