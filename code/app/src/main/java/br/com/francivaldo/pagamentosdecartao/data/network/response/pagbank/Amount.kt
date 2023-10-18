package br.com.confchat.mobile.data.network.response.pagbank

data class Amount(
    val currency: String,
    val summary: Summary,
    val value: Int
)