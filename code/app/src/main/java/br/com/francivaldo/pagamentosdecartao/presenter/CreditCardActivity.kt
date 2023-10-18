package br.com.francivaldo.pagamentosdecartao.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.confchat.mobile.presenter.view.screens.InsertPayCardInformScreen
import br.com.confchat.mobile.presenter.view.screens.InsertValuePaymentScreen
import br.com.francivaldo.pagamentosdecartao.presenter.common.CreditCardDoc
import br.com.francivaldo.pagamentosdecartao.presenter.common.RoutePay
import br.com.francivaldo.pagamentosdecartao.presenter.component.ComponentDialogStatePay
import br.com.francivaldo.pagamentosdecartao.presenter.enum.PaymentState
import br.com.francivaldo.pagamentosdecartao.presenter.screen.CustumerScreen
import br.com.francivaldo.pagamentosdecartao.presenter.theme.PagamentosDeCartaoTheme
import br.com.francivaldo.pagamentosdecartao.presenter.viewmodel.PagBankViewModel
import br.com.francivaldo.pagamentosdecartao.presenter.viewmodel.model.PaymentCreditCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCardActivity : ComponentActivity() {
    var doc = CreditCardDoc()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagamentosDeCartaoTheme {
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var paymentState by remember{
                        mutableStateOf(PaymentState.NONE)
                    }
                    val navController = rememberNavController()
                    val pagBankViewModel: PagBankViewModel = hiltViewModel()
                    val density = LocalDensity.current
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        NavHost(
                            navController = navController,
                            startDestination = RoutePay.InsertValue,
                            modifier = Modifier.padding(top = with(density){
                                WindowInsets.statusBars.getTop(density).toDp()
                            } )
                        ){
                            composable(RoutePay.InsertValue){
                                InsertValuePaymentScreen(navController, doc){
                                    doc.amont = it
                                }
                            }
                            composable(RoutePay.CardInform){
                                InsertPayCardInformScreen(navController,doc)
                            }
                            composable(RoutePay.Custumer){
                                CustumerScreen(navController,doc){
                                    paymentState = PaymentState.IN_PROCESS
                                    pagBankViewModel.initPaymentCreditCard(
                                        PaymentCreditCard(
                                            name = doc.name,
                                            cpf = doc.cpf,
                                            email = doc.email,
                                            amont = doc.amont.toInt(),
                                            expirationCard = doc.cardValidate,
                                            numberCard = doc.cardNumber,
                                            nameOnCard = doc.nameOnCard,
                                            cvv = doc.cvv
                                        )
                                    ){it,msg->
                                        if(it) {
                                            paymentState = PaymentState.SUCCESS
                                            doc = CreditCardDoc()
                                            navController.navigate(RoutePay.InsertValue){popUpTo(0)}
                                        }
                                        else {
                                            paymentState = PaymentState.ERROR
                                            Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ComponentDialogStatePay(paymentState){
                        paymentState = PaymentState.NONE
                    }
                }
            }
        }
    }
}