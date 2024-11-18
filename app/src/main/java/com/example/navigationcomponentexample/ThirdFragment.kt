package com.example.navigationcomponentexample

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.example.navigationcomponentexample.databinding.FragmentThirdBinding
import java.io.File

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var binding: FragmentThirdBinding
    private var imageCapture: ImageCapture? = null
    private var outputDirectory: File? = null
    private var savedUri: Uri? = null

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdBinding.bind(view)

        // Configuración inicial
        outputDirectory = getOutputDirectory()
        startCamera()

        // Botón para capturar foto
        binding.BtnCamara.setOnClickListener { takePhoto() }

        // Botón para mostrar imagen capturada
        binding.ivBtnSave.setOnClickListener {
            savedUri?.let {
                Toast.makeText(requireContext(), "Imagen guardada en: $it", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(requireContext(), "No hay imagen guardada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetResolution(Size(1280, 720))
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                imageCapture = ImageCapture.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

            } catch (e: Exception) {
                Log.e("CameraX", "Error iniciando la cámara", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val randomNumber = (100..999).shuffled().last()
        val photoFile = File(outputDirectory, "camara_personalizada_$randomNumber.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "Error al guardar la foto", exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    savedUri = Uri.fromFile(photoFile)
                    Toast.makeText(requireContext(), "Foto guardada en: $savedUri", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return mediaDir ?: requireContext().filesDir
    }
}
