package com.example.navigationcomponentexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class ThirdFragment : Fragment() {

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