package com.example.thermoapps.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thermoapps.R
import com.example.thermoapps.prediction.PredictionFragment
import kotlinx.android.synthetic.main.activity_result_prediction.*
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        info_home_page.text = resources.getText(R.string.home_page_info)
    }

    companion object {
        fun newInstance(): HomePageFragment = HomePageFragment()
    }
}