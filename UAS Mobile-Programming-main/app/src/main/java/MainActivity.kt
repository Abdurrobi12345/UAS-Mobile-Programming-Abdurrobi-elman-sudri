package com.abdurrobi.loginactivity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.example.com/"
    private lateinit var apiService: ApiService

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val bukaProfilButton = findViewById<Button>(R.id.bukaProfilButton)
        val bukaKameraButton = findViewById<Button>(R.id.bukaKameraButton)
        val tampilkanListViewButton = findViewById<Button>(R.id.tampilkanListViewButton)
        val tampilkanGambarButton = findViewById<Button>(R.id.tampilkanGambarButton)
        val keluarButton = findViewById<Button>(R.id.keluarButton)

        bukaProfilButton.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }

        bukaKameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        tampilkanListViewButton.setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }

        tampilkanGambarButton.setOnClickListener {
            try {
                startActivity(Intent(this, DisplayImagesActivity::class.java))
                Log.d(TAG, "Navigating to DisplayImagesActivity")
            } catch (e: Exception) {
                Log.e(TAG, "Error starting DisplayImagesActivity", e)
            }
        }

        keluarButton.setOnClickListener {
            finishAffinity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveImageToDatabase(imageBitmap)
        }
    }

    private fun saveImageToDatabase(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val dbHelper = DatabaseHelper(this)
        dbHelper.addPhoto(byteArray)
        Toast.makeText(this, "Gambar berhasil disimpan ke database", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Image saved to database")
    }
}
