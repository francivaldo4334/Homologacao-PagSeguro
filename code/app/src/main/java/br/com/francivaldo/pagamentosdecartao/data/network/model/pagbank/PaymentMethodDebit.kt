package br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank

import br.com.confchat.mobile.data.network.dto.pagbank.Card

data class PaymentMethodDebit (
    val capture: Boolean,
    val card: CardXX,
    val installments: Int,
    val soft_descriptor: String,
    val type: String,
    val authentication_method:AuthenticationMethod
)