package br.com.francivaldo.pagamentosdecartao.presenter.screen

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.francivaldo.pagamentosdecartao.R
import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment
import br.com.francivaldo.pagamentosdecartao.presenter.common.CreditCardDoc
import java.text.SimpleDateFormat
import java.util.Formatter
import java.util.logging.SimpleFormatter

@Composable
fun ScreenListPayment(list:List<Payment>,backDispa: OnBackPressedDispatcher) {
    val mask = CreditCardDoc().mask
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { backDispa.onBackPressed() }) {
                    Icon(imageVector = Icons.Default.ArrowBack,contentDescription = null)
                }
                Text(text = "Historico de pagamentos")
            }
        }
        items(list){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = mask(it.amount.toString()), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    if(it.success){
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Green)
                                .padding(8.dp)
                        ){
                            Text(text = stringResource(R.string.pago),color = MaterialTheme.colorScheme.background)
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Red)
                                .padding(8.dp)
                        ){
                            Text(text = stringResource(R.string.nao_pago),color = MaterialTheme.colorScheme.background)
                        }
                    }
                }
                Text(text = it.clientName, fontSize = 12.sp,color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                Text(text = formatter.format(it.createAt), fontSize = 12.sp,color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
            }
        }
    }
}
