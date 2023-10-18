package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class ErrorMessage(
    val code: String,
    val description: String,
    val parameter_name: String
)