package com.example.walletapp.data.model

data class Transaction(
    val id: Int = 0,// incrementado dps
    val tipo: String, // débito(gastos) e crédito(ganhos)
    val descricao: String, // descricao doq é
    val valor: Double // valor da operação
){
    override fun toString(): String {
        return tipo;
    }
}
