package br.com.confchat.mobile.data.database.repository.contract

import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment
import kotlinx.coroutines.flow.Flow


interface IPaymentRepository {
    fun create(it: Payment):Payment
    fun getList(): Flow<List<Payment>>
    fun getlast():Payment
    abstract fun update(payment: Payment)
}