package com.example.walletapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.walletapp.data.model.Transaction
import com.example.walletapp.data.db.DBHelper

class TransactionDAO(private val context: Context) {

    private val dbHelper = DBHelper(context)

   //CREATE
   fun createTrasaction(transaction: Transaction): Long {
       val db = dbHelper.writableDatabase
       val values = ContentValues().apply {
           put("tipo", transaction.tipo)
           put("descricao", transaction.descricao)
           put("valor", transaction.valor)
       }
       val id = db.insert(DBHelper.TABLE_NAME, null, values)
       db.close()
       return id
   }


    fun getAllTransactions(): List<Transaction> {
        val db = dbHelper.readableDatabase;
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null,null,null,null,null,null)
        val transactionList = mutableListOf<Transaction>()
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            transactionList.add(Transaction(id,tipo,descricao,valor));
        }
        cursor.close()
        db.close()
        return transactionList
    }

    fun getDebitoTransactions(): List<Transaction>{
        val db = dbHelper.readableDatabase;
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null,"tipo='debito'",null,null,null,null)
        val transactionList = mutableListOf<Transaction>()
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            transactionList.add(Transaction(id,tipo,descricao,valor));
        }
        cursor.close()
        db.close()
        return transactionList
    }

    fun getCreditoTransactions(): List<Transaction>{
        val db = dbHelper.readableDatabase;
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null,"tipo='credito'",null,null,null,null)
        val transactionList = mutableListOf<Transaction>()
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            transactionList.add(Transaction(id,tipo,descricao,valor));
        }
        cursor.close()
        db.close()
        return transactionList
    }


    fun calcularValor(): Double {
        var valoresDebito = getDebitoTransactions()
        var valoresCredito = getCreditoTransactions()
        var valorTotal = 0.0

        for (valor in valoresDebito) {
            valorTotal -= valor.valor
        }

        for (valor in valoresCredito) {
            valorTotal += valor.valor
        }
        return valorTotal
    }

}