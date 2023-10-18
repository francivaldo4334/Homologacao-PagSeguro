package br.com.confchat.mobile.data.network.dto.pagbank

data class AmountX(
    val currency: String,
    val summary: Summary,
    val value: Int
)