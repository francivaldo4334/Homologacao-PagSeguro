package br.com.confchat.mobile.presenter.view.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.francivaldo.pagamentosdecartao.R
import br.com.francivaldo.pagamentosdecartao.presenter.common.CreditCardDoc
import br.com.francivaldo.pagamentosdecartao.presenter.common.RoutePay
import br.com.francivaldo.pagamentosdecartao.presenter.theme.PagamentosDeCartaoTheme

@Composable
fun InsertValuePaymentScreen(navController: NavController, creditCardDoc: CreditCardDoc, onValue:(String)->Unit) {
    val context = LocalContext.current as Activity
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { context.finish() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                val numberButton: @Composable RowScope.(Int) -> Unit = { number ->
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.8f)
                            .clickable {
                                onValue(creditCardDoc.amont + number)
                            }) {
                        Text(
                            text = number.toString(),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = creditCardDoc.mask(creditCardDoc.amont),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        softWrap = false
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    FloatingActionButton(
                        onClick = {
                            if(creditCardDoc.amont.isNotBlank() && creditCardDoc.amont.toInt() > 99){
                                navController.navigate(RoutePay.CardInform)
                            }
                            else{
                                Toast.makeText(context,"Valor invalido",Toast.LENGTH_LONG).show()
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = null)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    numberButton(1)
                    numberButton(2)
                    numberButton(3)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    numberButton(4)
                    numberButton(5)
                    numberButton(6)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    numberButton(7)
                    numberButton(8)
                    numberButton(9)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    val button: @Composable (@Composable () -> Unit, () -> Unit) -> Unit =
                        { img, onClick ->
                            IconButton(
                                onClick = onClick, modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.8f)
                            ) { img() }
                        }
                    val buttonP: @Composable (Painter, () -> Unit) -> Unit = { img, onClick ->
                        button({ Icon(painter = img, contentDescription = null) }, onClick)
                    }
                    val buttonI: @Composable (ImageVector, () -> Unit) -> Unit = { img, onClick ->
                        button({ Icon(imageVector = img, contentDescription = null) }, onClick)
                    }
                    buttonI(Icons.Outlined.Check) {
                        if(creditCardDoc.amont.isNotBlank() && creditCardDoc.amont.toInt() > 99){
                            navController.navigate(RoutePay.CardInform)
                        }
                        else{
                            Toast.makeText(context,"Valor invalido",Toast.LENGTH_LONG).show()
                        }

                    }
                    numberButton(0)
                    buttonP(painterResource(id = R.drawable.ic_delete_text)) {
                        if (creditCardDoc.amont.length > 0) onValue(creditCardDoc.amont.substring(0, creditCardDoc.amont.length - 1))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InsertValuePaymentScreenPreview() {
    PagamentosDeCartaoTheme {
        InsertValuePaymentScreen(rememberNavController(),CreditCardDoc()) {}
    }
}