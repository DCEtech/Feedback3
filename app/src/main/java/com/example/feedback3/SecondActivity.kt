package com.example.feedback3


import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.feedback3.databinding.ActivitySecondBinding
import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException


class SecondActivity : AppCompatActivity(){
    lateinit var binding: ActivitySecondBinding
    lateinit var grabadora: MediaRecorder
    lateinit var play: MediaPlayer
    lateinit var ruta: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@SecondActivity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
                ),
                1000
            )
        }
    }

    fun grabar(view: View){
        ruta = getExternalFilesDir(null)?.absolutePath + "/grabacion.mp3"
                grabadora = MediaRecorder()
                grabadora.setAudioSource(MediaRecorder.AudioSource.MIC)
                grabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                grabadora.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                grabadora.setOutputFile(ruta)
                try{
                    grabadora.prepare()
                    grabadora.start()
                    Toast.makeText(applicationContext, "Inicio de grabación", Toast.LENGTH_SHORT).show()
                }catch(e: IOException){println(e)}
            }
    fun pausa(view: View){
            if (grabadora != null){
                grabadora.stop()
                grabadora.release()
                Toast.makeText(applicationContext, "Fin de grabación", Toast.LENGTH_SHORT).show()
            }

    }
    fun reproducir(view: View){
            try{
                play = MediaPlayer()
                play.setDataSource(ruta)
                play.prepare()

            }catch(e: IOException){println(e)}
            play.start()
            Toast.makeText(applicationContext, "Reproduciendo grabación", Toast.LENGTH_SHORT).show()
        }
    }



