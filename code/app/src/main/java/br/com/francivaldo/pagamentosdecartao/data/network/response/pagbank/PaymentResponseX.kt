package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class PaymentResponseX(
    val code: String,
    val message: String,
    val raw_data: RawData,
    val reference: String
)