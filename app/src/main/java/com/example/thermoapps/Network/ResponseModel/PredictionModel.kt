package com.example.thermoapps.Network.ResponseModel

import com.google.gson.annotations.SerializedName

data class PredictionModel (
    @SerializedName("status")
    var status:Boolean?,

    @SerializedName("prob")
    var prob:String?,

    @SerializedName("message")
    var message:String?,

    @SerializedName("image")
    var image:PredictedImageModel?
)