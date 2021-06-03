package com.example.mobilsorgular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_layout.view.*

class DataAdapter(private val doLocationId: ArrayList<String>, private val puLocationId: ArrayList<Float>): RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView : TextView?= null
        var textView2 = itemView.textView2

        init {
            textView = view.findViewById(R.id.textView)
            textView2 = view.findViewById(R.id.textView2)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView?.text = doLocationId[position].toString()
        holder.textView2?.text = puLocationId[position].toString()


    }

    override fun getItemCount(): Int {
        return doLocationId.size
    }
}