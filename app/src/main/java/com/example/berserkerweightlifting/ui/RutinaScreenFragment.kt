package com.example.berserkerweightlifting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.berserkerweightlifting.databinding.FragmentRutinaScreenBinding
import com.example.berserkerweightlifting.viewModel.AppViewModel
import org.checkerframework.checker.units.qual.s


class RutinaScreenFragment : Fragment() {
    private val sharedViewModel: AppViewModel by activityViewModels()

    private var _binding: FragmentRutinaScreenBinding? = null
    private val binding get() = _binding!!
    val args: RutinaScreenFragmentArgs by navArgs()
    lateinit var layout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRutinaScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val layout = binding.layoutRutina
        layout = binding.layoutRutina
        if(args.day == 0){
            val textLista = TextView(context)
            textLista.text = "NO HAY RUTINA PARA MOSTRAR"
            layout.addView(textLista)
        }else{
            //binding.ejercicio1.isVisible = true
            //binding.ejercicio2.isVisible = true

            sharedViewModel.getRutina(args.day.toString())
            sharedViewModel.rutina.observe(viewLifecycleOwner){
                layout.removeAllViews()
                val list = sharedViewModel.rutina.value!!
                for (s in list) {
                    val textLista = TextView(context)
                    textLista.text = s
                    layout.addView(textLista)
                }

                //binding.ejercicio1.text = sharedViewModel.rutina.value!!.get(0)
                //binding.ejercicio2.text = sharedViewModel.rutina.value!!.get(1)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        layout.removeAllViews()


    }

}