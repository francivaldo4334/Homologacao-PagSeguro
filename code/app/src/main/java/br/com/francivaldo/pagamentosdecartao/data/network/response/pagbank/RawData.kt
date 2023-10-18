package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class RawData(
    val authorization_code: String,
    val nsu: String,
    val reason_code: String
)