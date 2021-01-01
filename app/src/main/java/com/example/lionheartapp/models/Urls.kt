package com.example.lionheartapp.models


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class Urls(
    @SerializedName("full")
    val full:@RawValue String,
    @SerializedName("raw")
    val raw:@RawValue String,
    @SerializedName("regular")
    val regular:@RawValue String,
    @SerializedName("small")
    val small:@RawValue String,
    @SerializedName("thumb")
    val thumb:@RawValue String
)