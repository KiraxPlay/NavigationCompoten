package com.example.navigationcomponentexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root : View = inflater.inflate(R.layout.fragment_first, container, false)

        val btnGrabadora = root.findViewById<Button>(R.id.btnGrabadora)

        val btnCamara = root.findViewById<Button>(R.id.btnCamara)

        //Para el fragment de la grabadora
        btnGrabadora.setOnClickListener{
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
        }
        //Para el fragment de la camara
        btnCamara.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToThirdFragment())
        }

        return root
        }
    }