package com.example.thermoapps

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_result_prediction.*
import java.util.*

class ResultPrediction : AppCompatActivity() {
    private var imageBase64data: String = ""
    private var probabilityPrediction: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_prediction)
        imageBase64data = intent.getStringExtra("imageData")
        probabilityPrediction = intent.getStringExtra("probability")
        setupActionBar()
        setupImage()
        setupProbabilityData()
        setupInfoPrediction()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar!!.title = "Hasil Prediksi"
    }

    private fun setupImage() {
        Glide.with(this)
            .load(Base64.decode(imageBase64data,Base64.DEFAULT))
            .into(resultPrediction)
    }

    private fun setupProbabilityData() {
        probabilityPredictionTextView.text = "Probability = " + probabilityPrediction
    }

    private fun setupInfoPrediction() {
        infoPredictionTextView.text = "Hasil Prediksi ................"
    }
}