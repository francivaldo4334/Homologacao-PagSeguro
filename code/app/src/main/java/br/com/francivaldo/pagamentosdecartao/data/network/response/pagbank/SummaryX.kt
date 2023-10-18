package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class SummaryX(
    val paid: Int,
    val refunded: Int,
    val total: Int
)