package com.example.testbin.adapters

data class CardItemModel(
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: String,
    val bankName: String,
    val bankCity: String,
    val bankUrl: String,
    val bankPhone: String,
    val cardNumberLength: String,
    val cardNumberLuhn: String,
    val countryEmoji: String,
    val countryName: String,
    val countryLatitude: String,
    val countryLongitude: String
)