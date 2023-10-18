package br.com.francivaldo.pagamentosdecartao.domain.repository

import android.util.Log
import androidx.room.util.joinIntoString
import br.com.confchat.mobile.data.database.repository.contract.IPaymentRepository
import br.com.confchat.mobile.data.network.dto.pagbank.Amount
import br.com.confchat.mobile.data.network.dto.pagbank.Card
import br.com.confchat.mobile.data.network.dto.pagbank.Charge
import br.com.confchat.mobile.data.network.dto.pagbank.CreateOrderDto
import br.com.confchat.mobile.data.network.dto.pagbank.Customer
import br.com.confchat.mobile.data.network.dto.pagbank.Holder
import br.com.confchat.mobile.data.network.dto.pagbank.Item
import br.com.confchat.mobile.data.network.dto.pagbank.PaymentMethod
import br.com.confchat.mobile.data.network.repository.pagbank.IApiPagBankRepository
import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.AmountXX
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.CardXX
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.CreditCardPay
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.HolderXX
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.MetadataX
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.PaymentMethodXX
import br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank.CreditCardResponse
import br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank.CreditCardResponseError
import br.com.francivaldo.pagamentosdecartao.domain.model.getReference
import br.com.francivaldo.pagamentosdecartao.presenter.viewmodel.model.PaymentCreditCard
import kotlinx.coroutines.flow.Flow
import java.util.Date

class PagBankDomainRepository constructor(private val doc: IApiPagBankRepository, private val db: IPaymentRepository) :
    IPagBankDomainRepository {
    override fun createOrder(data: PaymentCreditCard):Pair<Boolean,String> {
        //tratar validade do cartao
        val expMonth = data.expirationCard.substring(0,2)
        val expYear = "20${data.expirationCard.substring(2,4)}"
        val payment = db.create(
            Payment(
                clientName = data.name,
                createAt = Date(),
                amount = data.amont,
                order = ""
            )
        )
        val dto = CreditCardPay(
            amount = AmountXX(
                currency = "BRL",
                value = data.amont
            ),
            description = "",
            metadata = MetadataX(),
            notification_urls = emptyList(),
            payment_method = PaymentMethodXX(
                capture = false,
                card = CardXX(
                    exp_month = expMonth,
                    exp_year = expYear,
                    holder = HolderXX(
                        name = data.nameOnCard
                    ),
                    number = data.numberCard,
                    security_code = data.cvv
                ),
                installments = 1,
                soft_descriptor = "",
                type = "CREDIT_CARD"
            ),
            reference_id = payment.getReference(),

        )
        val response = doc.creditCardPayment(dto)//TODO : obter estado do apagamento e armazenar ID
        Log.d(this@PagBankDomainRepository::class.java.simpleName,response.toString())
        when(response){
            is CreditCardResponse ->{
                if(response?.payment_response?.code == "20000") {
                    var payment = db.getlast()
                    payment.success = true
                    db.update(payment)
                    return Pair(true, "")
                }
            }
            is CreditCardResponseError ->{
                val stringError = response?.error_messages?.map { it.message }?.joinToString()?:""
                return Pair(false,stringError)
            }
        }
        return Pair(false,"Undefinid")
    }

    override fun createOrderDebit(data: PaymentCreditCard): Pair<Boolean, String> {
        //tratar validade do cartao
        val expMonth = data.expirationCard.substring(0,2)
        val expYear = "20${data.expirationCard.substring(2,4)}"
        val payment = db.create(
            Payment(
                clientName = data.name,
                createAt = Date(),
                amount = data.amont,
                order = ""
            )
        )
        val dto = CreditCardPay(
            amount = AmountXX(
                currency = "BRL",
                value = data.amont
            ),
            description = "",
            metadata = MetadataX(),
            notification_urls = emptyList(),
            payment_method = PaymentMethodXX(
                capture = false,
                card = CardXX(
                    exp_month = expMonth,
                    exp_year = expYear,
                    holder = HolderXX(
                        name = data.nameOnCard
                    ),
                    number = data.numberCard,
                    security_code = data.cvv
                ),
                installments = 1,
                soft_descriptor = "",
                type = "DEBIT_CARD"
            ),
            reference_id = payment.getReference(),

            )
        val response = doc.creditCardPayment(dto)//TODO : obter estado do apagamento e armazenar ID
        Log.d(this@PagBankDomainRepository::class.java.simpleName,response.toString())
        when(response){
            is CreditCardResponse ->{
                if(response?.payment_response?.code == "20000") {
                    var payment = db.getlast()
                    payment.success = true
                    db.update(payment)
                    return Pair(true, "")
                }
            }
            is CreditCardResponseError ->{
                val stringError = response?.error_messages?.map { it.message }?.joinToString()?:""
                return Pair(false,stringError)
            }
        }
        return Pair(false,"Undefinid")
    }

    override fun getlist(): Flow<List<Payment>> {
        return db.getList()
    }
}