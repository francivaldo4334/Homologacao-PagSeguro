package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class CreateOrderError(
    val error_messages: List<ErrorMessage>
)