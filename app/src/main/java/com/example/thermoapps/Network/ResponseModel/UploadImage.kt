package com.example.thermoapps.Network.ResponseModel

import com.google.gson.annotations.SerializedName

data class UploadImage (
    @SerializedName("status")
    var status:Boolean?,

    @SerializedName("message")
    var message:String?,

    @SerializedName("image")
    var image:String?,

    @SerializedName("current_format")
    var currentFormat:String?

)