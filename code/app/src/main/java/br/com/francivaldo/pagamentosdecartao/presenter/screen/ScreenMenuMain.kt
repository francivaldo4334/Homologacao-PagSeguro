package br.com.francivaldo.pagamentosdecartao.presenter.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.francivaldo.pagamentosdecartao.presenter.CreditCardActivity
import br.com.francivaldo.pagamentosdecartao.presenter.DebitCreditActivity
import br.com.francivaldo.pagamentosdecartao.presenter.HistoricoActivity

@Composable
fun ScreenMenuMain() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Opcoes")
        val itemButton: @Composable (text:String,onclick:()->Unit) -> Unit = { text,onclick->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
            ){
                Box(
                    modifier = Modifier
                        .clickable {
                            onclick()
                        }
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .wrapContentSize(Alignment.Center),
                ){
                    Text(text = text)
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item {
                itemButton("Credito"){
                    context.startActivity(Intent(context,CreditCardActivity::class.java))
                }
            }
            item {
                itemButton("Debito"){
                    context.startActivity(Intent(context,DebitCreditActivity::class.java))
                }
            }
            item {
                itemButton("Historico"){
                    context.startActivity(Intent(context,HistoricoActivity::class.java))
                }
            }
        }
    }
}

@Preview
@Composable
fun ScreenMenuMainPreview() {
    ScreenMenuMain()
}