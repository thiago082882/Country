package com.thiago.countryapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.thiago.countryapp.di.DaggerApiComponents;
import com.thiago.countryapp.model.CountriesService;
import com.thiago.countryapp.model.CountryModel;

import org.w3c.dom.ls.LSInput;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

//    private final CountriesService countriesService = CountriesService.getInstance();

    @Inject
    public CountriesService countriesService;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public ListViewModel(){
        super();
        DaggerApiComponents.create().inject(this);
    }
    public void refresh() {
        fetchCountries();
    }

//    private void fetchContries() {
//        CountryModel country1 = new CountryModel("Albania","Tirana","");
//        CountryModel country2 = new CountryModel("Brazil","Brasilia","");
//        CountryModel country3 = new CountryModel("Czechia","Praha","");
//
//        List<CountryModel> list = new ArrayList<>();
//        list.add(country1);
//        list.add(country2);
//        list.add(country3);
//
//        countries.setValue(list);
//        countryLoadError.setValue(false);
//        loading.setValue(false);
//
//    }

    private void fetchCountries() {

        loading.setValue(true);
        disposable.add(
                countriesService.getCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>(){

                            @Override
                            public void onSuccess(List<CountryModel> countryModels) {
                              countries.setValue(countryModels);
                              countryLoadError.setValue(false);
                              loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {

                                countryLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();

                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
