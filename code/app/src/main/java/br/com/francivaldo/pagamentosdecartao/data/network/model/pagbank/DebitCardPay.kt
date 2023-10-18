package br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank

data class DebitCardPay(
    val amount: AmountXX,
    val description: String,
    val metadata: MetadataX,
    val notification_urls: List<String>,
    val payment_method: PaymentMethodDebit,
    val reference_id: String
)