package br.com.francivaldo.pagamentosdecartao.domain.repository

import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment
import br.com.francivaldo.pagamentosdecartao.presenter.viewmodel.model.PaymentCreditCard
import kotlinx.coroutines.flow.Flow

interface IPagBankDomainRepository {
    fun createOrder(data: PaymentCreditCard):Pair<Boolean,String>
    abstract fun createOrderDebit(it: PaymentCreditCard): Pair<Boolean,String>
    fun getlist(): Flow<List<Payment>>
}