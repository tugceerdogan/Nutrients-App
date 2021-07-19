package com.example.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.util.gorselIndir
import com.example.besinlerkitabi.util.placeholderYap
import com.example.besinlerkitabi.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.besin_recycler_view.*
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {

    private lateinit var viewModel : BesinDetayiViewModel

    private var besinId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // gelen argumanı kontrol etme, alma :
        arguments?.let{
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java )
        viewModel.roomVerisiniAl(besinId)


        observeLiveData()


    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer{ besin->

            besin?.let{
                context?.let{
                    detayResmi.gorselIndir(besin.besinGorsel, placeholderYap(it))
                }

                detayAdi.text = it.besinAd
                detayKalori.text = it.besinKalori
                detayKarbonhidrat.text = it.besinKarbonhidrat
                detayProtein.text = it.besinProtein
                detayYag.text = it.besinYag
            }

        })

    }


}