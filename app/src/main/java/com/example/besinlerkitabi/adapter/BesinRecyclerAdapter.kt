package com.example.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.model.Besin
import com.example.besinlerkitabi.util.gorselIndir
import com.example.besinlerkitabi.util.placeholderYap
import com.example.besinlerkitabi.view.BesinListesiFragment
import com.example.besinlerkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_view.view.*
import kotlinx.android.synthetic.main.fragment_besin_detayi.view.*

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {

    class BesinViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.besin_recycler_view,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.itemView.besinAdi.text = besinListesi.get(position).besinAd
        holder.itemView.besinKalorisi.text = besinListesi.get(position).besinKalori
        //gorsel eklenecek :

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(0)
            Navigation.findNavController(it).navigate(action)
        }

        //holder.itemView.besinGorsel.gorselIndir(besinListesi.get(position).besinGorsel, placeholderYap(holder.itemView.context))

        holder.itemView.besinResmi.gorselIndir(besinListesi.get(position).besinGorsel,placeholderYap(holder.itemView.context))


    }

    override fun getItemCount(): Int {
        return besinListesi.size

    }

    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }
}