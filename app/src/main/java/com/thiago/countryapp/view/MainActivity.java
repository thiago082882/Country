package com.thiago.countryapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.thiago.countryapp.R;
import com.thiago.countryapp.databinding.ActivityMainBinding;
import com.thiago.countryapp.model.CountryModel;
import com.thiago.countryapp.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListViewModel viewModel;
    private final CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>());
   private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.refresh();

        binding.countriesList.setLayoutManager(new LinearLayoutManager(this));
        binding.countriesList.setAdapter(adapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        
        observerViewModel();

    }

    private void observerViewModel() {
        viewModel.countries.observe(this, new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if(countryModels != null ){
                    binding.countriesList.setVisibility(View.VISIBLE);
                    adapter.updateCoutries(countryModels);
                }
            }
        });

        viewModel.countryLoadError.observe(this,isError -> {
            if(isError != null){
                binding.listError.setVisibility(isError ? View.VISIBLE : View.GONE);

            }
        });
        viewModel.loading.observe(this,loading->{
            if(loading !=null){
                binding.loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
           if (loading){
               binding.listError.setVisibility(View.GONE);
               binding.countriesList.setVisibility(View.GONE);
           }
            }
        });

    }
}