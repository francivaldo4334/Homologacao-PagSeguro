package br.com.confchat.mobile.data.network.dto.pagbank

data class ChargeX(
    val amount: AmountX,
    val created_at: String,
    val description: String,
    val id: String,
    val links: List<LinkX>,
    val metadata: Metadata,
    val payment_method: PaymentMethodX,
    val payment_response: PaymentResponse,
    val reference_id: String,
    val status: String
)