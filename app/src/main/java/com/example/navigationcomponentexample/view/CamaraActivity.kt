package com.example.navigationcomponentexample.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.cristhian.miprimeraapp.Constants
import com.cristhian.miprimeraapp.Constants.REQUIRED_PERMISSIONS
import com.example.navigationcomponentexample.R
import com.example.navigationcomponentexample.databinding.ActivityCamaraBinding
import java.io.File
import android.content.Intent
import kotlin.math.log

class CamaraActivity : AppCompatActivity() {

    lateinit var binding: ActivityCamaraBinding

    //inicializando variables para la captura de camara
    private var preview:Preview? = null
    private var imageCapture:ImageCapture? = null
    private var imageAnalysis:ImageAnalysis? = null
    private var camera:Camera? = null


    //guardando foto
    private lateinit var outputDirectory: File
    var photoFile: File? = null
    var savedUri: Uri? = null


    //variable para el temporizador
    private lateinit var timer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCamaraBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(allPermissionsGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        outputDirectory = getOutputDirectory()

        binding.BtnCamara.setOnClickListener { takePhoto() }

        binding.ivBtnSave.setOnClickListener {
            val intent = Intent(this, ImagenCapturadaActivity::class.java)
            intent.putExtra("uri", savedUri.toString())
            startActivity(intent)
        }
    }

    private fun allPermissionsGranted() =
        REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable{
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder()
                .setTargetResolution(Size(1280, 720))
                .build()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            try {
                cameraProvider.unbindAll()
                imageCapture= ImageCapture.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .build()

                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalysis
                )
                preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)

            } catch (e: Exception) {
                Log.e(Constants.TAG, "FALLO LA CAMARA", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePhoto(){
        timer = object : CountDownTimer(6_000, 1_000) {
            override fun onTick(remaining: Long) {
                binding.BtnCamara.isVisible = false
                binding.tvTimer.isVisible = true
                binding.tvTimer.text = (remaining/1000).toString()
                if (remaining < 1_000)
                    binding.tvTimer.text = "\uD83D\uDE09"
            }

            override fun onFinish() {
                takePhoto()
                binding.tvTimer.isVisible = false
                binding.ivBtnSave.isVisible = true
            }
        }
        timer.start()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }




    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if(requestCode == Constants.REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera()
            }else{
                Toast.makeText(this,
                    "Permisos Denegados",
                    Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }
}

