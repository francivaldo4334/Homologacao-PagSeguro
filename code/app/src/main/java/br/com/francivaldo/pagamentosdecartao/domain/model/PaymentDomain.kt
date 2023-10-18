package br.com.francivaldo.pagamentosdecartao.domain.model

import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment

fun Payment.getReference():String{
    return "sale-${this.id}"
}