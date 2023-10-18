package br.com.francivaldo.pagamentosdecartao.presenter.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.francivaldo.pagamentosdecartao.presenter.enum.PaymentState
import kotlinx.coroutines.delay

@Composable
fun ComponentDialogStatePay(state: PaymentState,onDismiss:()->Unit) {
    if(state != PaymentState.NONE){
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    when(state){
                        PaymentState.SUCCESS ->{
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .border(
                                        2.dp, Color.Green,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(imageVector = Icons.Outlined.Check, contentDescription = null,tint = Color.Green)
                            }
                            LaunchedEffect(key1 = Unit){
                                delay(2000)
                                onDismiss()
                            }
                        }
                        PaymentState.ERROR ->{
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .border(
                                        2.dp, MaterialTheme.colorScheme.error,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                            }
                            LaunchedEffect(key1 = Unit){
                                delay(2000)
                                onDismiss()
                            }
                        }
                        else ->{
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun ComponentDialogStatePayPreveiw() {
    ComponentDialogStatePay(PaymentState.SUCCESS){}
}