package br.com.confchat.mobile.data.network.repository.pagbank

import android.util.Log
import br.com.confchat.mobile.data.network.dto.pagbank.CheckOrderResponse
import br.com.confchat.mobile.data.network.dto.pagbank.CreateOrderDto
import br.com.confchat.mobile.data.network.response.pagbank.Amount
import br.com.confchat.mobile.data.network.response.pagbank.Card
import br.com.confchat.mobile.data.network.response.pagbank.Charge
import br.com.confchat.mobile.data.network.response.pagbank.CreateOrderResponse
import br.com.confchat.mobile.data.network.response.pagbank.Customer
import br.com.confchat.mobile.data.network.response.pagbank.Holder
import br.com.confchat.mobile.data.network.response.pagbank.PaymentMethod
import br.com.confchat.mobile.data.network.response.pagbank.PaymentResponse
import br.com.confchat.mobile.data.network.response.pagbank.Summary
import br.com.francivaldo.pagamentosdecartao.data.network.ApiaPagBankService
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.CreditCardPay
import br.com.francivaldo.pagamentosdecartao.data.network.model.pagbank.DebitCardPay
import br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank.CreateOrderError
import br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank.CreditCardResponse
import br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank.CreditCardResponseError
import br.com.francivaldo.pagamentosdecartao.data.network.response.pagbank.ErrorMessageX
import com.google.gson.Gson

class ApiPagBankRepository constructor(private val api: ApiaPagBankService) :
    IApiPagBankRepository {
    override fun createOrder(it: CreateOrderDto): CreateOrderResponse? {
        val call = api.createOrder(it)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                return response.body()!!
            }
            val erro = Gson().fromJson(response.errorBody()?.string(),CreateOrderError::class.java)
            return CreateOrderResponse(
                charges = buildList { add(Charge(
                        Amount("", Summary(0,0,0),0),
                        "",
                        "d",
                        "",
                        emptyList(),
                        PaymentMethod(
                                false,
                                "",
                                Card("","","","", Holder(""),"",false),
                            0,
                            "",
                            ""
                        ),
                        PaymentResponse(
                            "",
                            erro.error_messages.first().description,
                            ""
                        ),
                        "",
                        ""

                    ))

                },
                created_at = "",
                customer = Customer("","",""),
                id = "",
                items = buildList {  },
                links = buildList {  },
                notification_urls = buildList {  },
                reference_id = ""
            )
        } catch (e: Exception) {
            Log.e(this@ApiPagBankRepository::class.java.simpleName, e.message.toString())
            return null
        }
    }

    override fun checkOrder(it: String): CheckOrderResponse? {
        val call = api.checkOrder(it)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                return response.body()!!
            }
            return null
        } catch (e: Exception) {
            Log.e(this@ApiPagBankRepository::class.java.simpleName, e.message.toString())
            return null
        }
    }

    override fun creditCardPayment(dto: CreditCardPay): Any{
        val call = api.creditCardPayment(dto)
        try {
            val resp = call.execute()
            if(resp.isSuccessful)
                return resp.body()!!
            return Gson().fromJson(resp.errorBody()?.string(),CreditCardResponseError::class.java)
        }
        catch (e:Exception){
            return CreditCardResponseError(
                buildList {
                    add(ErrorMessageX(
                        code = "501",
                        description = e.message.toString(),
                        message = e.message.toString(),
                        parameter_name = ""
                    ))
                }
            )
        }
    }

    override fun debitCardPayment(dto: DebitCardPay): Any {
        val call = api.debitCardPayment(dto)
        try {
            val resp = call.execute()
            if(resp.isSuccessful)
                return resp.body()!!
            return Gson().fromJson(resp.errorBody()?.string(),CreditCardResponseError::class.java)
        }
        catch (e:Exception){
            return CreditCardResponseError(
                buildList {
                    add(ErrorMessageX(
                        code = "501",
                        description = e.message.toString(),
                        message = e.message.toString(),
                        parameter_name = ""
                    ))
                }
            )
        }
    }
}