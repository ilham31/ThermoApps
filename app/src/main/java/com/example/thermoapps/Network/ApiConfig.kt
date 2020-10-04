package com.example.thermoapps.Network

import com.example.thermoapps.Network.ResponseModel.PredictionModel
import com.example.thermoapps.Network.ResponseModel.UploadImage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class ApiConfig{
    companion object {
        // base url dari end point.
        const val BASE_URL = "http://thermo.apps.cs.ipb.ac.id/predictors/"
    }

    // init retrofit
    private fun retrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun instance() : ApiInterface {
        return retrofit().create(ApiInterface::class.java)
    }
}

// interface dari retrofit
interface ApiInterface{
    @Multipart
    @POST("upload-image") // endpoint upload
    fun upload(
        @Part file:MultipartBody.Part
    ) : Call<UploadImage> // response

    @FormUrlEncoded
    @POST("process")
    fun processPrediction(
        @Field("image_path")imagePath:String
    ) : Call<PredictionModel>
}