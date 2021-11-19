package com.korkmazanil.countrieslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.korkmazanil.countrieslist.R
import com.korkmazanil.countrieslist.model.Country
import com.korkmazanil.countrieslist.util.downloadFromUrl
import com.korkmazanil.countrieslist.util.placeHolderProgressBar
import com.korkmazanil.countrieslist.view.FeedFragmentDirections

class CountryAdapter(private val countryList : ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

        var name : TextView = view.findViewById(R.id.name)
        var region : TextView = view.findViewById(R.id.region)
        var imageView : ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.name.text = countryList[position].countryName
        holder.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}