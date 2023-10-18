package br.com.confchat.mobile.data.network.dto.pagbank

data class Charge(
    val amount: Amount,
    val description: String,
    val payment_method: PaymentMethod,
    val reference_id: String
)