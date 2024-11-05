package com.example.navigationcomponentexample.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.media.MediaPlayer
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.graphics.Color
import androidx.core.app.ActivityCompat
import com.example.navigationcomponentexample.R
import com.example.navigationcomponentexample.databinding.ActivityGrabadoraBinding
import java.io.IOException

class GrabadoraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGrabadoraBinding
    private var grabadora: MediaRecorder? = null
    private var ruta: String? = null
    private var isRecording: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGrabadoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Accediendo a los botones
        binding.btnGrabar.setOnClickListener { grabar(it) }
        binding.btnPlay.setOnClickListener { reproducir(it) }
        binding.btnPausar.setOnClickListener { detener(it) }

        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 0
            )
        }
    }

    private fun detener(view: View?) {
        if (isRecording) {
            try {
                grabadora?.stop()
                grabadora?.release()
                binding.btnGrabar.setBackgroundColor(Color.BLACK)
                isRecording = false
                Toast.makeText(applicationContext, "Grabación detenida", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Error al detener la grabación", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "No hay grabación en curso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun grabar(view: View?) {
        if (isRecording) {
            detener(view)
        } else {
            // Configurar la ruta de almacenamiento
            ruta = getExternalFilesDir(null)?.absolutePath + "/grabacion.mp3"
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
                    Toast.makeText(applicationContext, "Grabando...", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(applicationContext, "Reproduciendo audio", Toast.LENGTH_SHORT).show()

            // Liberar MediaPlayer al finalizar
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error al reproducir el audio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }
    }
}