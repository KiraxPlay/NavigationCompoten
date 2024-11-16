package com.example.navigationcomponentexample

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.navigationcomponentexample.databinding.FragmentSecondBinding
import java.io.File
import java.io.IOException

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val args: SecondFragmentArgs by navArgs()

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioFile: String? = null
    private var isRecording = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        audioFile = "${requireActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/audio_record.mp3"

        binding.btnGrabar.setOnClickListener {
            if (checkPermissions()) {
                if (!isRecording) {
                    startRecording()
                } else {
                    stopRecording()
                }
            }
        }

        binding.btnPlay.setOnClickListener {
            playRecording()
        }

        binding.btnPausar.setOnClickListener {
            pauseRecording()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permissionsNeeded = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
            return false
        }
        return true
    }

    private fun startRecording() {
        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile)
                prepare()
                start()
            }
            isRecording = true
            binding.btnGrabar.text = "Detener Grabación"
            Toast.makeText(requireContext(), "Grabación iniciada", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
        binding.btnGrabar.text = "Grabar"
        Toast.makeText(requireContext(), "Grabación detenida", Toast.LENGTH_SHORT).show()
    }

    private fun playRecording() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(audioFile)
                    prepare()
                    start()
                } catch (e: IOException) {
                    Toast.makeText(requireContext(), "Error al reproducir el audio", Toast.LENGTH_SHORT).show()
                }
            }
            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
    }

    private fun pauseRecording() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaRecorder?.release()
        mediaRecorder = null
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200
    }
}