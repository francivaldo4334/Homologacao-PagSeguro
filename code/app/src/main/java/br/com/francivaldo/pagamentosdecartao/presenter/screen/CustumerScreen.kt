package br.com.francivaldo.pagamentosdecartao.presenter.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.francivaldo.pagamentosdecartao.presenter.common.CreditCardDoc
import br.com.francivaldo.pagamentosdecartao.presenter.component.ComponentButton1
import br.com.francivaldo.pagamentosdecartao.presenter.component.ComponentOutlineTextFild
import br.com.francivaldo.pagamentosdecartao.presenter.component.VisualTransformationCpf

@Composable
fun CustumerScreen(navController: NavController, doc: CreditCardDoc, onInitPayment: () -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val labelCpf = "Cpf:"
    val textFild: @Composable (
        String,
        KeyboardType,
        String,
        VisualTransformation,
        (String) -> Unit
    ) -> Unit =
        { label, keyboardType, value, vt, onValue ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = label, modifier = Modifier.padding(bottom = 4.dp))
                ComponentOutlineTextFild(
                    value = value,
                    onChange = onValue,
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    keyboardType = keyboardType,
                    imeAction = {
                        if(label.equals(labelCpf)){
                            focusManager.clearFocus()
                        }
                        else{
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    },
                    visualTransformation = vt
                )
            }
        }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                Text(text = "Dados do cliente", fontSize = 18.sp)
            }
            Divider()
            textFild("Nome:", KeyboardType.Text, doc.name, VisualTransformation.None) {
                doc.name = it
            }
            textFild("Email:", KeyboardType.Email, doc.email, VisualTransformation.None) {
                doc.email = it
            }
            textFild(labelCpf, KeyboardType.Number, doc.cpf, VisualTransformationCpf()) {
                if (it.length <= 11)
                    doc.cpf = it
            }
        }
        item {
            ComponentButton1(text = "Avancar") {
                val emailRgx = Regex("^[a-z.]+@[a-z.]+$")
//                val nameRgx = Regex("^[A-Za-z]+\\s[A-Za-z\\s]+$")
                if(
                    doc.name.isNotBlank() && doc.name.contains(" ")&&
                    doc.email.isNotBlank() && emailRgx.matches(doc.email) &&
                    doc.cpf.isNotBlank() && doc.cpf.length > 10
                ){
                    onInitPayment()
                }
                else{
                    Toast.makeText(context,"Erro: Verifique os dados e tente novamente.", Toast.LENGTH_LONG).show()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun CustumerScreenPreview() {
    CustumerScreen(rememberNavController(), CreditCardDoc()) {

    }
}