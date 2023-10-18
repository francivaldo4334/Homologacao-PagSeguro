package br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank

data class CardXX(
    val exp_month: String,
    val exp_year: String,
    val holder: HolderXX,
    val number: String,
    val security_code: String
)