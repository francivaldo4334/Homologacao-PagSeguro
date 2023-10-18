package br.com.confchat.mobile.data.network.dto.pagbank

data class Card(
    val exp_month: Int,
    val exp_year: Int,
    val holder: Holder,
    val number: String,
    val security_code: String,
//    val store: Boolean
)