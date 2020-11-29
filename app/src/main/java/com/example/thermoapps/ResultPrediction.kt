package com.example.thermoapps

import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Base64
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_result_prediction.*
import java.io.File
import java.util.*

class ResultPrediction : AppCompatActivity() {
    private var imageBase64data: String = ""
    private var probabilityPrediction: String = ""
    private lateinit var inputFilePrediction: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_prediction)
        imageBase64data = intent.getStringExtra("imageData")
        probabilityPrediction = intent.getStringExtra("probability")
        inputFilePrediction = intent.getSerializableExtra("input_image") as File
        prediction_input_textview.typeface = Typeface.DEFAULT_BOLD
        hasil_prediksi.typeface = Typeface.DEFAULT_BOLD
        setupActionBar()
        setupImage()
        setupProbabilityData()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar!!.title = "Hasil Prediksi"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupImage() {
        if (inputFilePrediction.exists()) {
            input_prediction.setImageURI(Uri.fromFile(inputFilePrediction))
        }

        Glide.with(this)
            .load(Base64.decode(imageBase64data,Base64.DEFAULT))
            .into(resultPrediction)
    }

    private fun setupProbabilityData() {
        val percentage = probabilityPrediction.toDouble() * 100;
        val formattedValue = String.format("%.2f%%", percentage)
        val spannableString = SpannableString("Peluang status kehamilan dari input image adalah " + formattedValue)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(boldSpan, 49, 49+formattedValue.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        probabilityPredictionTextView.text = spannableString
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}