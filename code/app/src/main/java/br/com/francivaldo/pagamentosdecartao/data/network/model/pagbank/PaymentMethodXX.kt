package br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank

data class PaymentMethodXX(
    val capture: Boolean,
    val card: CardXX,
    val installments: Int,
    val soft_descriptor: String,
    val type: String
)