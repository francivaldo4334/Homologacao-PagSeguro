package br.com.confchat.mobile.data.network.dto.pagbank

data class CheckOrderResponse(
    val charges: List<ChargeX>,
    val created_at: String,
    val customer: CustomerX,
    val id: String,
    val items: List<ItemX>,
    val links: List<LinkX>,
    val notification_urls: List<Any>,
    val reference_id: String
)