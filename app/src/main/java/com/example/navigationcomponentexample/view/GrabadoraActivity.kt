package com.example.navigationcomponentexample.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.example.navigationcomponentexample.databinding.ActivityGrabadoraBinding

class GrabadoraActivity : AppCompatActivity() {
    lateinit var binding: ActivityGrabadoraBinding

    lateinit private var grabadora:MediaRecorder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGrabadoraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //accediendo a los botones
        binding.btnGrabar.setOnClickListener{}
        binding.btnPlay.setOnClickListener{}
        binding.btnPausar.setOnClickListener{}
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
    //private fun grabar(view: View?){
      //  if(::grabadora.isInitialized && grabadora.state == MediaRecorder.State)
    //}
}