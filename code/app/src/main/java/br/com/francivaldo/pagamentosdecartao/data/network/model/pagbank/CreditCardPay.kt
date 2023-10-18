package br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank

data class CreditCardPay(
    val amount: AmountXX,
    val description: String,
    val metadata: MetadataX,
    val notification_urls: List<String>,
    val payment_method: PaymentMethodXX,
    val reference_id: String
)