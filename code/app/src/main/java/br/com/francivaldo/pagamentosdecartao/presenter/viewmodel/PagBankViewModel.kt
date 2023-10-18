package br.com.francivaldo.pagamentosdecartao.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.francivaldo.pagamentosdecartao.presenter.viewmodel.model.PaymentCreditCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import br.com.francivaldo.pagamentosdecartao.domain.repository.IPagBankDomainRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PagBankViewModel @Inject constructor(private val domain: IPagBankDomainRepository) : ViewModel(){
    val listPayment = domain.getlist().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )
    fun initPaymentCreditCard(data: PaymentCreditCard,onResult:(Boolean,String)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = domain.createOrder(data)
            viewModelScope.launch(Dispatchers.Main) {
                onResult(result.first,result.second)
            }
        }
    }
    fun initPaymentDebitCard(data: PaymentCreditCard,onResult:(Boolean,String)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = domain.createOrderDebit(data)
            viewModelScope.launch(Dispatchers.Main) {
                onResult(result.first,result.second)
            }
        }
    }
}