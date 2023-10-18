package br.com.confchat.mobile.data.network.dto.pagbank

data class CardX(
    val brand: String,
    val exp_month: String,
    val exp_year: String,
    val first_digits: String,
    val holder: HolderX,
    val last_digits: String
)