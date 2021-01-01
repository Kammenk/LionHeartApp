package com.example.lionheartapp.models


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class Sponsor(
    @SerializedName("accepted_tos")
    val acceptedTos:@RawValue Boolean,
    @SerializedName("bio")
    val bio:@RawValue String,
    @SerializedName("first_name")
    val firstName:@RawValue String,
    @SerializedName("id")
    val id:@RawValue String,
    @SerializedName("instagram_username")
    val instagramUsername:@RawValue String,
    @SerializedName("last_name")
    val lastName:@RawValue String,
    @SerializedName("location")
    val location:@RawValue Any,
    @SerializedName("name")
    val name:@RawValue String,
    @SerializedName("portfolio_url")
    val portfolioUrl:@RawValue String,
    @SerializedName("profile_image")
    val profileImage:@RawValue ProfileImage,
    @SerializedName("total_collections")
    val totalCollections:@RawValue Int,
    @SerializedName("total_likes")
    val totalLikes:@RawValue Int,
    @SerializedName("total_photos")
    val totalPhotos:@RawValue Int,
    @SerializedName("twitter_username")
    val twitterUsername:@RawValue String,
    @SerializedName("updated_at")
    val updatedAt:@RawValue String,
    @SerializedName("username")
    val username:@RawValue String
)