package com.example.navigationcomponentexample

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.navigationcomponentexample.databinding.FragmentImagenCapturadaBinding

class ImagenCapturadaFragment : Fragment(R.layout.fragment_imagen_capturada) {

    private lateinit var binding: FragmentImagenCapturadaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImagenCapturadaBinding.bind(view)

        // Recibe el argumento enviado por Safe Args
        val uriString = arguments?.let { ImagenCapturadaFragmentArgs.fromBundle(it).uri }

        if (uriString != null) {
            // Mostrar la imagen recibida
            binding.ivImagenCapturada.setImageURI(Uri.parse(uriString))
        } else {
            Toast.makeText(requireContext(), "No se recibió ninguna imagen", Toast.LENGTH_SHORT).show()
        }
    }
}
