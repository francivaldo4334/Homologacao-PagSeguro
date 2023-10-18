package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class AmountX(
    val currency: String,
    val summary: SummaryX,
    val value: Int
)