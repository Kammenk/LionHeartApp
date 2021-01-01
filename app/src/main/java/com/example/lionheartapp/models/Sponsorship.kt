package com.example.lionheartapp.models


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class Sponsorship(
    @SerializedName("impression_urls")
    val impressionUrls:@RawValue List<String>,
    @SerializedName("sponsor")
    val sponsor: @RawValue Sponsor,
    @SerializedName("tagline")
    val tagline:@RawValue String,
    @SerializedName("tagline_url")
    val taglineUrl:@RawValue String
)