package com.thiago.countryapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    Single<List<CountryModel>> getCountry();


}
