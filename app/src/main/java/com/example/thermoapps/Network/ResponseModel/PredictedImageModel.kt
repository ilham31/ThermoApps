package com.example.thermoapps.Network.ResponseModel

import com.google.gson.annotations.SerializedName

data class PredictedImageModel (
    @SerializedName("type")
    var type:String?,

    @SerializedName("base64_data")
    var base64Data:String?
)