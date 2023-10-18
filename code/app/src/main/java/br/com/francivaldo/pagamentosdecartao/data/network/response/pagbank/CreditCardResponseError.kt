package br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank

data class CreditCardResponseError(
    val error_messages: List<ErrorMessageX>
)