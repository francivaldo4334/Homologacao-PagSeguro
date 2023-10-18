package br.com.confchat.mobile.data.database.repository.implementation

import br.com.confchat.mobile.data.database.repository.contract.IPaymentRepository
import br.com.francivaldo.pagamentosdecartao.data.database.dao.PaymentDao
import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment
import kotlinx.coroutines.flow.Flow

class PaymentRepository constructor(private val db: PaymentDao) : IPaymentRepository {
    override fun create(it: Payment): Payment {
        db.insert(it)
        var response = db.getlast()
        return response
    }

    override fun getList(): Flow<List<Payment>> {
        return db.getAll()
    }

    override fun getlast(): Payment {
        return db.getlast()
    }

    override fun update(payment: Payment) {
        db.update(payment)
    }
}