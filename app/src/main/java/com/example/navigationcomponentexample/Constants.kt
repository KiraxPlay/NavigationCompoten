package com.example.navigationcomponentexample

import android.Manifest
object Constants {
    const val REQUEST_CODE_PERMISSIONS = 777

    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    const val TAG = "CameraXBasic"
    const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    const val PHOTO_TYPE = "image/jpeg"
    const val RATIO_4_3_VALUE = 4.0 / 3.0
    const val RATIO_16_9_VALUE = 16.0 / 9.0
}