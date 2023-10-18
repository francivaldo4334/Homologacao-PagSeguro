package br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank

data class AuthenticationMethod(
    val cavv: String,
    val dstrans_id: String,
    val eci: String,
    val type: String,
    val version: String,
    val xid: String
)