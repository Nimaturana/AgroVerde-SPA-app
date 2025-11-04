package com.example.agroverdespamovil.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class LocalImageStorage(private val context: Context) {

    private val profileImageDir = File(context.filesDir, "profile_images")

    init {
        if (!profileImageDir.exists()) {
            profileImageDir.mkdirs()
        }
    }

    // Guardar imagen de perfil
    suspend fun saveProfileImage(imageUri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val outputFile = File(profileImageDir, "profile_image.jpg")

            inputStream?.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }

            Uri.fromFile(outputFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Obtener imagen de perfil
    fun getProfileImage(): Uri? {
        val file = File(profileImageDir, "profile_image.jpg")
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            null
        }
    }

    // Eliminar imagen de perfil
    fun deleteProfileImage(): Boolean {
        val file = File(profileImageDir, "profile_image.jpg")
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }
}