package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class PaymentMethodX(
    val capture: Boolean,
    val capture_before: String,
    val card: CardX,
    val installments: Int,
    val soft_descriptor: String,
    val type: String
)