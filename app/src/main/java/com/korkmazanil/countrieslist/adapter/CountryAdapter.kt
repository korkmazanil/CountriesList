package com.korkmazanil.countrieslist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.korkmazanil.countrieslist.R
import com.korkmazanil.countrieslist.databinding.ItemCountryBinding
import com.korkmazanil.countrieslist.model.Country
import com.korkmazanil.countrieslist.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList : ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener  {

    class CountryViewHolder(var view : ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil
            .inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)

        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country = countryList[position]
        holder.view.listener = this
        /*
        holder.name.text = countryList[position].countryName
        holder.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        // XML içerisinden Utile @BindingAdapter ile yazılan metod çağırıldı.
        holder.imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))
        */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(view: View) {

        val uuid = view.uuid.text.toString().toInt()
        val action = FeedFragmentDirections
            .actionFeedFragmentToCountryFragment(uuid)
        //action.countryUuid
        Navigation.findNavController(view).navigate(action)
    }


}