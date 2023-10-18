package br.com.confchat.mobile.data.network.response.pagbank

data class PaymentResponse(
    val code: String,
    val message: String,
    val reference: String
)