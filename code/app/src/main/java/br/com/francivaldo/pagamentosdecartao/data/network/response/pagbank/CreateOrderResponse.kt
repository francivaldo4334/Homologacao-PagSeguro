package br.com.confchat.mobile.data.network.response.pagbank

data class CreateOrderResponse(
    val charges: List<Charge>,
    val created_at: String,
    val customer: Customer,
    val id: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val notification_urls: List<Any>,
    val reference_id: String
)