package br.com.confchat.mobile.data.network.dto.pagbank

data class PaymentMethod(
    val capture: Boolean,
    val card: Card,
    val installments: Int,
    val type: String
)