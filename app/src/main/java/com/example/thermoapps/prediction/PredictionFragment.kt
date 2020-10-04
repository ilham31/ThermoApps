package com.example.thermoapps.prediction

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.thermoapps.FileUtil
import com.example.thermoapps.Network.ApiConfig
import com.example.thermoapps.Network.ResponseModel.PredictionModel
import com.example.thermoapps.Network.ResponseModel.UploadImage
import com.example.thermoapps.R
import com.example.thermoapps.ResultPrediction
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_prediction.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PredictionFragment : Fragment() {
    private val TAG: String = "AppDebug"
    private val GALLERY_REQUEST_CODE = 1234
    lateinit var imageFile:MultipartBody.Part

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pick_from_galery.setOnClickListener {
            pickFromGallery()
        }

        sample_positive.setOnClickListener {
            val file = FileUtil.fileFromDrawable(context, R.drawable.sample_positive)
            imageFile =  MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file))
            setImageFromDrawable(R.drawable.sample_positive)
        }

        sample_negative.setOnClickListener {
            val file = FileUtil.fileFromDrawable(context, R.drawable.sample_negative)
            imageFile =  MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file))
            setImageFromDrawable(R.drawable.sample_negative)
        }

        submit_predict.setOnClickListener {
            uploadImage()
        }

        predictionButtonState(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prediction, container, false)
    }

    companion object {
        fun newInstance(): PredictionFragment = PredictionFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                       launchImageCrop(uri)
                    }
                }
                else{
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory." )
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val file = FileUtil.fromURI(context,result.uri)
                    imageFile =  MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file))
                    setImage(result.uri)
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e(TAG, "Crop error: ${result.getError()}" )
                }
            }
        }
    }

    private fun setImageFromDrawable(id: Int) {
        Glide.with(this)
            .load(id)
            .into(image)
        val density = context?.resources?.displayMetrics?.density
        image.layoutParams.height = (150 * density!!).toInt()
        predictionButtonState(true)
    }

    private fun setImage(uri: Uri){
        Glide.with(this)
            .load(uri)
            .into(image)
        val density = context?.resources?.displayMetrics?.density
        image.layoutParams.height = (150 * density!!).toInt()
        predictionButtonState(true)
    }

    private fun launchImageCrop(uri: Uri){
        context?.let {
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE) // default is rectangle
                .start(it, this)
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun predictionButtonState(isAlreadySelectPicture: Boolean) {
        if (isAlreadySelectPicture){
            submit_predict.setBackgroundColor(resources.getColor(R.color.colorButtonPrimary))
            submit_predict.setTextColor(Color.WHITE)
        } else {
            submit_predict.setBackgroundColor(Color.GRAY)
            submit_predict.setTextColor(Color.BLACK)
        }
        submit_predict.isEnabled = isAlreadySelectPicture
    }

    private fun uploadImage() {
        progress_predict.visibility = View.VISIBLE
        progress_predict.bringToFront()
        val call = ApiConfig().instance().upload(imageFile)
        call.enqueue(object : retrofit2.Callback<UploadImage> {
            // handling request saat fail
            override fun onFailure(call: Call<UploadImage>?, t: Throwable?) {
                progress_predict.visibility = View.GONE
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                Log.d("ONFAILURE", t.toString())
            }

            // handling request saat response.
            override fun onResponse(call: Call<UploadImage>?, response: Response<UploadImage>?) {
                // menampilkan pesan yang diambil dari response.

                Toast.makeText(context, response?.body()?.message, Toast.LENGTH_SHORT).show()
                val status = response?.body()?.status
                if (status!!) {
                    val imagePath = response.body()!!.image
                    if (imagePath != null) {
                        Log.d("image path", imagePath)
                        predictImage(imagePath)
                    }
                }  else {
                    Toast.makeText(context, response?.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private  fun predictImage(imagePath: String) {
        val predict = ApiConfig().instance().processPrediction(imagePath)
            predict.enqueue(object : Callback<PredictionModel>{
                override fun onFailure(call: Call<PredictionModel>, t: Throwable) {
                    progress_predict.visibility = View.GONE
                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                    Log.d("ONFAILURE", t.toString())
                }

                override fun onResponse(
                    call: Call<PredictionModel>,
                    response: Response<PredictionModel>
                ) {
                    progress_predict.visibility = View.GONE
                    val successPredict = response.body()?.status
                    if (successPredict!!) {
                        val intent = Intent(activity, ResultPrediction::class.java)
                        intent.putExtra("imageData", response.body()!!.image?.base64Data)
                        intent.putExtra("probability", response.body()!!.prob)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, response?.body()?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
}