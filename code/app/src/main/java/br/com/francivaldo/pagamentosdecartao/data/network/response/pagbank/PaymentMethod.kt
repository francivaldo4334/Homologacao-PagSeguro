package br.com.confchat.mobile.data.network.response.pagbank

data class PaymentMethod(
    val capture: Boolean,
    val capture_before: String,
    val card: Card,
    val installments: Int,
    val soft_descriptor: String,
    val type: String
)