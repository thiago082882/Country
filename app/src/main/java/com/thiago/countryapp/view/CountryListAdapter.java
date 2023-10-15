package com.thiago.countryapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thiago.countryapp.R;
import com.thiago.countryapp.databinding.ItemCountryBinding;
import com.thiago.countryapp.model.CountryModel;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private List<CountryModel> countries;

    public CountryListAdapter(List<CountryModel> countries) {
        this.countries = countries;
    }

    public void updateCoutries(List<CountryModel> newCountries) {
        countries.clear();
        countries.addAll(newCountries);

    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bind(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        private ItemCountryBinding binding;

        public CountryViewHolder(ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CountryModel country) {

            binding.name.setText(String.valueOf(country.getCountryName()));
            binding.capital.setText(String.valueOf(country.getCapital()));
//            Glide.with(binding.imageView.getContext())
//                    .load(country.getFlag())
//                    .into(binding.imageView);
            Util.loadImage(binding.imageView,country.getFlag(),Util.getProgressDrawable(binding.imageView.getContext()));

        }
    }
}
