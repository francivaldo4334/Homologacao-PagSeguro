package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class CreditCardResponse(
    val amount: AmountX,
    val created_at: String,
    val description: String,
    val id: String,
    val links: List<Link>,
    val metadata: Metadata,
    val notification_urls: List<Any>,
    val payment_method: PaymentMethodX,
    val payment_response: PaymentResponseX,
    val reference_id: String,
    val status: String
)