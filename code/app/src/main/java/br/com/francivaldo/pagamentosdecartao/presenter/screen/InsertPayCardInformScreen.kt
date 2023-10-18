package br.com.confchat.mobile.presenter.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.francivaldo.pagamentosdecartao.presenter.common.CreditCardDoc
import br.com.francivaldo.pagamentosdecartao.presenter.common.RoutePay
import br.com.francivaldo.pagamentosdecartao.presenter.component.ComponentButton1
import br.com.francivaldo.pagamentosdecartao.presenter.component.ComponentOutlineTextFild
import br.com.francivaldo.pagamentosdecartao.presenter.theme.PagamentosDeCartaoTheme

private var focusInNumberCard by mutableStateOf(false)
private var focusInNameOnCard by mutableStateOf(false)
private var focusInValidateCard by mutableStateOf(false)
@Composable
private fun CardLayout(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                val color = MaterialTheme.colorScheme.primary
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .background(color.copy(0.3f)), onDraw = {
                    val w = size.width
                    val h = size.height
                    val blocksize1 = w * 0.68f
                    val blocksize2 = w * 1.2f

                    val vector: (Float) -> Path = {
                        val x = w * 0.27f + it
                        var y = ((blocksize1 - h) / 2) + it
                        val path = Path().apply {
                            arcTo(
                                Rect(
                                    offset = Offset(0f - x, y),
                                    size = Size(blocksize1, blocksize1)
                                ),
                                -180f,
                                180f,
                                false
                            )
                            arcTo(
                                Rect(
                                    offset = Offset(blocksize1 - x, y),
                                    size = Size(blocksize2, blocksize1)
                                ),
                                -180f,
                                -180f,
                                false
                            )
                            lineTo(0f, h * 2)
                            lineTo(-(w / 3), h / 2)

                        }
                        path
                    }
                    rotate(-30f) {
                        drawPath(path = vector(-(w / 8)), color = color.copy(0.6f))
                        drawPath(path = vector(0f), color = color)
                    }

                })
                content()
            }
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FrontCard(creditCardDoc: CreditCardDoc) {
    CardLayout {
        val chip: @Composable () -> Unit = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.12f)
                    .aspectRatio(1.3f)
                    .border(0.5.dp, Color.Black, RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Divider(color = Color.Black)
                    Divider(color = Color.Black)
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp), color = Color.Black
                )
            }
        }
        val contentSelector: @Composable (Boolean, @Composable () -> Unit) -> Unit = { v, c ->
            Box{
                AnimatedVisibility(
                    visible = v,
                    enter = scaleIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                1.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                    ) {
                        c()
                    }
                }
                AnimatedVisibility(
                    visible = !v,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(modifier = Modifier.padding(4.dp)) {
                        c()
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth()
            ) {
                chip()
                //TODO: Bland card
            }
            contentSelector(focusInNumberCard) {
                val mask: (String) -> String = {
                    val value = it
                    val default = "################"
                    var result = ""
                    default.forEachIndexed { index, c ->
                        if(index == 4 || index == 8 || index == 12){
                            result += ' '
                        }
                        if(index < value.length)
                            result += value[index]
                        else
                            result += c
                    }
                    result
                }
                Text(
                    text = mask(creditCardDoc.cardNumber),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Nome impresso no cartao",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    contentSelector(focusInNameOnCard) {
                        Text(
                            text = if (creditCardDoc.nameOnCard.isNotEmpty()) creditCardDoc.nameOnCard else "Nome Completo",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.weight(1f),
                            softWrap = false,
                            maxLines = 1
                        )
                    }
                }
                Column {
                    Text(
                        text = "Validade",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    contentSelector(focusInValidateCard) {
                        Text(
                            text = creditCardDoc.maskValid(creditCardDoc.cardValidate),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BackCard(creditCardDoc:CreditCardDoc) {
    CardLayout {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(thickness = 40.dp, color = Color.Black)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                val inputString = creditCardDoc.cvv
                val maskedString = buildString {
                    for (i in inputString.indices) {
                        append("*")
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(text = maskedString)
                }
            }
            Box {
                //TODO: Bland Card
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Card(font: Boolean,creditCardDoc:CreditCardDoc) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = font,
            enter = scaleIn() + expandHorizontally(expandFrom = Alignment.CenterHorizontally),
            exit = fadeOut()

        ) {
            FrontCard(creditCardDoc)
        }
        AnimatedVisibility(
            visible = !font,
            enter = scaleIn() + expandHorizontally(expandFrom = Alignment.CenterHorizontally),
            exit = fadeOut()
        ) {
            BackCard(creditCardDoc)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun InsertPayCardInformScreen(navController: NavController,creditCardDoc: CreditCardDoc) {
    val focusManager = LocalFocusManager.current
    var visibleFront by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val textFild: @Composable (
        KeyboardType,
        String,
        Float,
        (Boolean)->Unit,
        (String) -> Unit,
        () -> String
    ) -> Unit =
        { keyboardType,label, fill, onFocus, onValue, value ->
            Column {
                Text(text = label, modifier = Modifier.padding(bottom = 4.dp))
                ComponentOutlineTextFild(
                    value = value(),
                    onChange = onValue,
                    modifier = Modifier
                        .fillMaxWidth(fill),
                    onFocus = onFocus,
                    keyboardType = keyboardType,
                    imeAction = {
                        if(label == "Nome no cartao:"){
                            if(
                                creditCardDoc.cardNumber.length > 15 &&
                                creditCardDoc.cardValidate.length > 3 &&
                                creditCardDoc.cvv.length > 2 &&
                                creditCardDoc.nameOnCard.isNotBlank()
                            ){
                                navController.navigate(RoutePay.Custumer)
                            }
                            else{
                                Toast.makeText(context,"Erro: verifique os dados e tente novamente.",Toast.LENGTH_LONG).show()
                            }
                        }
                        else {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    }
                )
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Text(
                text = creditCardDoc.mask(creditCardDoc.amont),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 16.dp, top = 16.dp)
            )
        }
        Card(visibleFront,creditCardDoc )
        Divider()
        LazyColumn(
            Modifier.weight(1f)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Dados do cartao",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    textFild(
                        KeyboardType.Number,
                        "Numero do cartao:",
                        1f,
                        {
                            focusInNumberCard = it
                        },
                        {
                            if(it.length <= 16)
                                creditCardDoc.cardNumber = it
                        }
                    ) {
                        creditCardDoc.cardNumber
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        textFild(
                            KeyboardType.Number,
                            "Validade do cartao:",
                            0.6f,
                            {
                                focusInValidateCard = it
                            },
                            {
                                if(it.length<=4)
                                    creditCardDoc.cardValidate = it
                            }
                        ) {
                            creditCardDoc.cardValidate
                        }
                        textFild(
                            KeyboardType.Number,
                            "cvv:",
                            1f,
                            {
                                visibleFront = !it
                            },
                            {
                                if(it.length <= 3)
                                    creditCardDoc.cvv = it
                            }
                        ) {
                            creditCardDoc.cvv
                        }
                    }
                    textFild(
                        KeyboardType.Text,
                        "Nome no cartao:",
                        1f,
                        {
                            focusInNameOnCard = it
                        },
                        {
                            creditCardDoc.nameOnCard = it.uppercase()
                        }
                    ) {
                        creditCardDoc.nameOnCard
                    }
                }
            }
        }
        ComponentButton1(text = "Avancar") {
            if(
                creditCardDoc.cardNumber.length > 15 &&
                creditCardDoc.cardValidate.length > 3 &&
                creditCardDoc.cvv.length > 2 &&
                creditCardDoc.nameOnCard.isNotBlank()
            ){
                navController.navigate(RoutePay.Custumer)
            }
            else{
                Toast.makeText(context,"Erro: verifique os dados e tente novamente.",Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview
@Composable
private fun InsertPayCardInformScreenPreview() {
    PagamentosDeCartaoTheme {
        InsertPayCardInformScreen(rememberNavController(), CreditCardDoc())
    }
}