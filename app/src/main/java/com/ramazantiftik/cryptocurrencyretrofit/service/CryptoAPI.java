package com.ramazantiftik.cryptocurrencyretrofit.service;

import com.ramazantiftik.cryptocurrencyretrofit.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET, DATA, UPDATE, DELETE

    //https://raw.githubusercontent.com/ --> base url
    //atilsamancioglu/K21-JSONDataSet/master/crypto.json --> key

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json") // key
    Observable<List<CryptoModel>> getData();

    //Call<List<CryptoModel>> getData();

}
