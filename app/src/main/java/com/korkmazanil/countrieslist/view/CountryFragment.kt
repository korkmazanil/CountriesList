package com.korkmazanil.countrieslist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.korkmazanil.countrieslist.R
import com.korkmazanil.countrieslist.databinding.FragmentCountryBinding
import com.korkmazanil.countrieslist.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel : CountryViewModel
    private lateinit var binding : FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom()

        observeLiveData()

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
    }

    private fun observeLiveData() {

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country ->
            country?.let{
                binding.countryName.text = country.countryName
                binding.countryRegion.text = country.countryRegion
                binding.countryCapital.text = country.countryCapital
                binding.countryLanguage.text = country.countryLanguage
                binding.countryCurrency.text = country.countryCurrency


            }
        })
    }
}