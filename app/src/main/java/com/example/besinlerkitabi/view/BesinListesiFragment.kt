package com.example.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.example.besinlerkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    //view model obje oluşturma :
    private lateinit var viewModel : BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //butona basınca besin detay fragmenta gitme olayı :
        //besin_listesi_button.setOnClickListener {
        //    val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(3)
        //    Navigation.findNavController(it).navigate(action)

       // }


        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        besinListesiRecyclerView.layoutManager = LinearLayoutManager(context)
        besinListesiRecyclerView.adapter = recyclerBesinAdapter


        swipeRefreshLayout.setOnRefreshListener{

            besinYukleniyorProgressBar.visibility = View.VISIBLE
            besinHataMesaji.visibility = View.GONE
            besinListesiRecyclerView.visibility = View.GONE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false

        }


        observeLiveData()


    }

    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer {besinler ->
            besinler?.let{
                besinListesiRecyclerView.visibility = View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)

            }
        })
        
        viewModel.besinHataMesaji.observe(viewLifecycleOwner,Observer {hata->
            hata?.let{
                if(it){
                    besinHataMesaji.visibility =View.VISIBLE
                    besinListesiRecyclerView.visibility = View.GONE
                }else{
                    besinHataMesaji.visibility =View.GONE
                }
            }

        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner,Observer { yukleniyor->
            yukleniyor?.let{
                if(it){

                    besinListesiRecyclerView.visibility = View.GONE
                    besinHataMesaji.visibility = View.GONE
                    besinYukleniyorProgressBar.visibility=View.VISIBLE

                }else{
                    besinYukleniyorProgressBar.visibility = View.GONE
                }
            }

        })

    }

}