package br.com.confchat.mobile.data.network.response.pagbank

data class Summary(
    val paid: Int,
    val refunded: Int,
    val total: Int
)