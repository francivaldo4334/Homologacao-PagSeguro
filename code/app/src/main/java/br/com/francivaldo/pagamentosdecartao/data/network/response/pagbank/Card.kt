package br.com.confchat.mobile.data.network.response.pagbank

data class Card(
    val brand: String,
    val exp_month: String,
    val exp_year: String,
    val first_digits: String,
    val holder: Holder,
    val last_digits: String,
    val store: Boolean
)