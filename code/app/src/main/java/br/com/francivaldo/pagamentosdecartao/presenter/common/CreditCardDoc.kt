package br.com.francivaldo.pagamentosdecartao.presenter.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class CreditCardDoc {
    var name by  mutableStateOf("")
    var email by  mutableStateOf("")
    var cpf by  mutableStateOf("")
    var amont by mutableStateOf("")
    var cardNumber by mutableStateOf("")
    var cardValidate by mutableStateOf("")
    var cvv by mutableStateOf("")
    var nameOnCard by mutableStateOf("")
    val mask: (String) -> String = {
        var value = it
        if (value.length < 3) {
            value = when {
                value.length == 2 -> "0$value"
                value.length == 1 -> "00$value"
                else -> "000"
            }
        }
        val regex = Regex("(\\d+)(\\d{2})")
        val result = value.replace(regex, "$1,$2")
        "R$ $result"
    }
    val maskValid: (String) -> String = {
        var value = it
        if (value.length < 4) {
            value = when {
                value.length == 3 -> "${value}X"
                value.length == 2 -> "${value}XX"
                value.length == 1 -> "${value}XXX"
                else -> "XXXX"
            }
        }
        var result = ""
        value.forEachIndexed { index, c ->
            if(index == 2)
                result +="/"
            result += c
        }
        result
    }
}