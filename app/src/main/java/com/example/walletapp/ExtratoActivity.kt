package com.example.walletapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletapp.dao.TransactionDAO
import com.example.walletapp.data.model.Transaction

class ExtratoActivity : AppCompatActivity() {

    private lateinit var transactionDAO: TransactionDAO
    private lateinit var extratoAdapter: ExtratoAdapter
    private lateinit var extratoRecyclerView: RecyclerView

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioTudo: RadioButton
    private lateinit var radioCredito: RadioButton
    private lateinit var radioDebito: RadioButton

    private lateinit var textTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)

        transactionDAO = TransactionDAO(this)
        extratoRecyclerView = findViewById(R.id.extratoRV)

        radioGroup = findViewById(R.id.radioGroup)
        radioTudo = findViewById(R.id.radioTudo)
        radioCredito = findViewById(R.id.radioCredito)
        radioDebito = findViewById(R.id.radioDebito)

        textTotal = findViewById<TextView>(R.id.textTotal)
        textTotal.setText(transactionDAO.calcularValor().toString())
        setupRecyclerView()
        setupFilterListener()
        loadTransactions()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        extratoAdapter = ExtratoAdapter(this)
        extratoRecyclerView.adapter = extratoAdapter
        extratoRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupFilterListener() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            loadTransactions()
        }
    }

    private fun loadTransactions() {
        val transactions = when {
            radioCredito.isChecked -> transactionDAO.getCreditoTransactions()
            radioDebito.isChecked -> transactionDAO.getDebitoTransactions()
            else -> transactionDAO.getAllTransactions()
        }
        extratoAdapter.atualizarDados(transactions)
    }

    private fun adicionarDadosDeTesteSeVazio() {
        if (transactionDAO.getAllTransactions().isEmpty()) {
            transactionDAO.createTrasaction(Transaction(tipo = "credito", descricao = "Salário de Setembro", valor = 4200.00))
            transactionDAO.createTrasaction(Transaction(tipo = "debito", descricao = "Aluguel", valor = 1500.00))
            transactionDAO.createTrasaction(Transaction(tipo = "debito", descricao = "Compras no Supermercado", valor = 550.75))
        }
    }


    fun voltar(view: View) {
        finish()
    }
}