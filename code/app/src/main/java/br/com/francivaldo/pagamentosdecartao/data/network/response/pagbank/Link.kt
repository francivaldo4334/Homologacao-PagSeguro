package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class Link(
    val href: String,
    val media: String,
    val rel: String,
    val type: String
)