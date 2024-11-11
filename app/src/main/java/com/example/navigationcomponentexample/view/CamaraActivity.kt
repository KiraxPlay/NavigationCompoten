package com.example.navigationcomponentexample.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cristhian.miprimeraapp.Constants
import com.cristhian.miprimeraapp.Constants.REQUIRED_PERMISSIONS
import com.example.navigationcomponentexample.databinding.ActivityCamaraBinding
import java.io.File

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
            Toast.makeText(this,
                "Has aceptado los permisos",
                Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.BtnCamara.setOnClickListener { takePhoto() }

    }

    private fun allPermissionsGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }



    private fun startCamera(){}

    private fun takePhoto(){}
    

}