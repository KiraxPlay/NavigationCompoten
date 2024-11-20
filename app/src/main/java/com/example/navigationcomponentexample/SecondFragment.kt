package com.example.navigationcomponentexample

import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.navigationcomponentexample.databinding.FragmentSecondBinding
import java.io.IOException

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private var grabadora: MediaRecorder? = null
    private var ruta: String? = null
    private var isRecording: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        // Configurar botones
        binding.btnGrabar.setOnClickListener { grabar(it) }
        binding.btnPlay.setOnClickListener { reproducir(it) }
        binding.btnPausar.setOnClickListener { detener(it) }



        return binding.root
    }

    private fun detener(view: View?) {
        if (isRecording) {
            try {
                grabadora?.stop()
                grabadora?.release()
                grabadora = null
                binding.btnGrabar.setBackgroundColor(Color.BLACK)
                isRecording = false
                Toast.makeText(requireContext(), "Grabación detenida", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error al detener la grabación", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "No hay grabación en curso", Toast.LENGTH_SHORT).show()
        }
    }



    private fun grabar(view: View?) {
        if (isRecording) {
            detener(view)
        } else {
            ruta = requireContext().getExternalFilesDir(null)?.absolutePath + "/grabacion.mp3"
            grabadora = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(ruta)
                try {
                    prepare()
                    start()
                    binding.btnGrabar.setBackgroundColor(Color.RED)
                    isRecording = true
                    Toast.makeText(requireContext(), "Grabando...", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun reproducir(view: View?) {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(ruta)
            mediaPlayer.prepare()
            mediaPlayer.start()
            Toast.makeText(requireContext(), "Reproduciendo audio", Toast.LENGTH_SHORT).show()

            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al reproducir el audio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
