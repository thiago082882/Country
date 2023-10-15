package com.thiago.countryapp.di;

import com.thiago.countryapp.model.CountriesService;
import com.thiago.countryapp.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponents {
    void inject(CountriesService service);

    void inject(ListViewModel viewModel);
}
