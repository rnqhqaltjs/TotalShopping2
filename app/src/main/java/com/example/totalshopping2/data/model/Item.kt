package com.example.totalshopping2.data.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Item(
    @field:Json(name = "brand")
    val brand: String,
    @field:Json(name = "category1")
    val category1: String,
    @field:Json(name = "category2")
    val category2: String,
    @field:Json(name = "category3")
    val category3: String,
    @field:Json(name = "category4")
    val category4: String,
    @field:Json(name = "hprice")
    val hprice: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "link")
    val link: String,
    @field:Json(name = "lprice")
    val lprice: String,
    @field:Json(name = "maker")
    val maker: String,
    @field:Json(name = "mallName")
    val mallName: String,
    @field:Json(name = "productId")
    val productId: String,
    @field:Json(name = "productType")
    val productType: String,
    @field:Json(name = "title")
    val title: String
) : Parcelable