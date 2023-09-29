package com.dicoding.submissiongithubapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.submissiongithubapp.R

class SplashScreenActivity : AppCompatActivity() {
    // deklarasi variabel durasi splash screen tampil selama 3 detik
    private val DURASI_SPLASH: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // run main activity setelah durasi splash sreen selesai
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, DURASI_SPLASH)
    }
}