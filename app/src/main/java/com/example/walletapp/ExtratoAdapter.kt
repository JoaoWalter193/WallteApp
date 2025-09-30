package com.example.walletapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.walletapp.data.model.Transaction

class ExtratoAdapter(private val context: Context)
    : RecyclerView.Adapter<ExtratoAdapter.ExtratoViewHolder>() {

    private var transacoes: List<Transaction> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtratoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        return ExtratoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExtratoViewHolder,
        position: Int
    ) {
        val transacao = transacoes[position]
        holder.transacaoName.text = transacao.descricao
        holder.transacaoValue.text = transacao.valor.toString()

        if (transacao.tipo.equals("credito", ignoreCase = true)) {
            holder.transacaoImage.setImageResource(R.drawable.add_circle)

        } else {
            // Use o nome do seu arquivo PNG de débito aqui
            holder.transacaoImage.setImageResource(R.drawable.remove)
        }
    }

    override fun getItemCount(): Int {
        return transacoes.size
    }

    fun atualizarDados(novasTransacoes: List<Transaction>) {
        this.transacoes = novasTransacoes
        notifyDataSetChanged()
    }

    inner class ExtratoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transacaoImage = itemView.findViewById<ImageView>(R.id.transacaoImage)
        val transacaoName = itemView.findViewById<TextView>(R.id.transacaoName)
        val transacaoValue = itemView.findViewById<TextView>(R.id.transacaoValue)
    }
}