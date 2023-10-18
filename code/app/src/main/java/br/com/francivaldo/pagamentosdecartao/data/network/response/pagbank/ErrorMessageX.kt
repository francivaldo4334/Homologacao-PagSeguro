package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class ErrorMessageX(
    val code: String,
    val description: String,
    val message: String,
    val parameter_name: String
)