package com.example.navigationcomponentexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class SecondFragment : Fragment() {

    val args:SecondFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name:String = args.name //Accediendo a name que creamos en el maph_graph y tambien
        //en el firstfragment.kt
        val tvName = view.findViewById<TextView>(R.id.tvName)
        tvName.text = name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View = inflater.inflate(R.layout.fragment_second, container, false)
        val btnNavi = root.findViewById<Button>(R.id.btnNavi)

        btnNavi.setOnClickListener{
            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
        }
        return root
    }
}