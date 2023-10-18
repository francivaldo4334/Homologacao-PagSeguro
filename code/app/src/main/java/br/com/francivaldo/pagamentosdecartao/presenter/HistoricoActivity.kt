package br.com.francivaldo.pagamentosdecartao.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.francivaldo.pagamentosdecartao.presenter.screen.ScreenListPayment
import br.com.francivaldo.pagamentosdecartao.presenter.screen.ScreenMenuMain
import br.com.francivaldo.pagamentosdecartao.presenter.theme.PagamentosDeCartaoTheme
import br.com.francivaldo.pagamentosdecartao.presenter.viewmodel.PagBankViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoricoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val viewmodel:PagBankViewModel = hiltViewModel()
            val historico by viewmodel.listPayment.collectAsState()
            // Set up the back button handler
            val backDispatcher = onBackPressedDispatcher
            PagamentosDeCartaoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ScreenListPayment(list = historico,backDispatcher)
                    }
                }
            }
        }
    }
}