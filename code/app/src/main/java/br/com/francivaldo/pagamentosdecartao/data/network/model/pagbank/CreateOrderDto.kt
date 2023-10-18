package br.com.confchat.mobile.data.network.dto.pagbank

data class CreateOrderDto(
    val charges: List<Charge>,
    val customer: Customer,
    val items: List<Item>,
    val reference_id: String
)