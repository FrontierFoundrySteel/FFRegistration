package com.ffsteel.ffpractice_app1.Models


import com.google.gson.annotations.SerializedName

data class UserRegistrationResponseDataModel(
    @SerializedName("response")
    var response: String,
    @SerializedName("database")
    var database: String,
    @SerializedName("user")
    var user: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("ip")
    var ip: String,
    @SerializedName("port")
    var port: String
)



