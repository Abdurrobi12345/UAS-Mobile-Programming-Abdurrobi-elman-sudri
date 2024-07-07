package com.abdurrobi.loginactivity

import com.google.gson.annotations.SerializedName

data class UnPam(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)
