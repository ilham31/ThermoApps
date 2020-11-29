package com.example.thermoapps.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thermoapps.R
import com.example.thermoapps.prediction.PredictionFragment
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_prediction.*

class AboutFragment : Fragment() {

    private val html: String =
        """
            <html>
            <head>
            <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
            <style type="text/css">
            body {
                font-family: 'Roboto-Light';
                font-size: 14px;
                text-align: justify;
            }
            
            li p {
                margin-top: 4px;
                margin-bottom: 0px;
                padding: 0;
                color: #3b413c;
            }
            </style>
            </head>
            <body>
                <b>Akuisisi data: </b>
                <p>Pengguna dapat mengambil citra termal monyet ekor Panjang dengan menggunakan kamera termal seperti FLIR ONE Pro dan Seek CompactPro.</p><br><br>
                <b>Proses: </b>
                <ol>
                    <li><p>Pengguna memilih citra termal yang akan diprediksi</p></li>
                    <li><p>Citra termal diunggah di server</p></li>
                    <li><p>Proses prediksi menggunakan Convolutional Neural Network: identifikasi daerah abdomen dan hitung peluang kehamilan dari objek yang diprediksi</p></li>
                    <li><p>Server mengirimkan hasil prediksi ke perangkat pengguna</p></li>
                    <li><p>Pengguna memperoleh hasil prediksi tentang status kehamilan monyet ekor panjang</p></li>
                </ol><br><br>
                
                <b>Acknowledge:</b>
                <p>Penelitian ini didanai oleh Penelitian Terapan Unggulan Perguruan Tinggi (PTUPT), IPB University 2019-2020</p>
            </body>
            </html>
        """.trimIndent()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        about_app.loadDataWithBaseURL(null,html,"text/html","utf-8", null)
    }

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }
}