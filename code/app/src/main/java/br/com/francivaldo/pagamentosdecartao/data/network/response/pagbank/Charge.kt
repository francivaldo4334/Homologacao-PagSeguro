package br.com.confchat.mobile.data.network.response.pagbank

data class Charge(
    val amount: Amount,
    val created_at: String,
    val description: String,
    val id: String,
    val links: List<LinkX>,
    val payment_method: PaymentMethod,
    val payment_response: PaymentResponse,
    val reference_id: String,
    val status: String
)