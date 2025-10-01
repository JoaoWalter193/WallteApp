package com.example.walletapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.walletapp.dao.TransactionDAO
import com.example.walletapp.data.model.Transaction

class CadastroActivity : AppCompatActivity() {
    private lateinit var textViewDescricao: EditText;
    private lateinit var numberViewValor: EditText;
    private lateinit var spinnerView: Spinner;

    private lateinit var transactionDAO: TransactionDAO;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        transactionDAO = TransactionDAO(this)
    }

    fun cadastrarTransacao(view: View) {
        textViewDescricao = findViewById<EditText>(R.id.editTextDescricao)
        spinnerView = findViewById<Spinner>(R.id.spinnerOpcoes)

        if ((textViewDescricao.length() == 0) || (textViewDescricao.length() == 0)){
            Toast.makeText(this, "Preencha todos os campos corretamente!!", Toast.LENGTH_SHORT).show()
        } else {


            val newTransaction = Transaction(
                tipo = spinnerView.selectedItem.toString(),
                descricao = textViewDescricao.text.toString(),
                valor = pegarDoubleValor()
            )
            transactionDAO.createTrasaction(newTransaction)
            Toast.makeText(this, spinnerView.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            textViewDescricao.setText("")

        }

    }
    fun pegarDoubleValor(): Double {
        numberViewValor = findViewById<EditText>(R.id.editTextNumberValor)
        val double = numberViewValor.text.toString().replace(",", ".")
        return try {
            numberViewValor.setText("")
            double.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    fun voltar(view: View) {
        finish()
    }



}