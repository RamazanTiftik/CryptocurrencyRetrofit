package com.ramazantiftik.cryptocurrencyretrofit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ramazantiftik.cryptocurrencyretrofit.R;
import com.ramazantiftik.cryptocurrencyretrofit.adapter.CryptoAdapter;
import com.ramazantiftik.cryptocurrencyretrofit.databinding.ActivityMainBinding;
import com.ramazantiftik.cryptocurrencyretrofit.model.CryptoModel;
import com.ramazantiftik.cryptocurrencyretrofit.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> modelList;
    private String BASE_URL="https://raw.githubusercontent.com/";
    Retrofit retrofit;
    private ActivityMainBinding binding;
    private CryptoAdapter adapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        //https://raw.githubusercontent.com/https://raw.githubusercontent.com/ --> base url
        //atilsamancioglu/K21-JSONDataSet/master/crypto.json --> key

        //Retrofit & JSON
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    private void loadData(){

        final CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class);
        compositeDisposable=new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));



        /*Call<List<CryptoModel>> call=cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){

                    List<CryptoModel> responseList=response.body();
                    modelList=new ArrayList<>(responseList); // modelList=responseList

                    //recyclerView
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter=new CryptoAdapter(modelList);
                    binding.recyclerView.setAdapter(adapter);

                    for(CryptoModel cryptoModel : modelList){
                        System.out.println(cryptoModel.currency);
                        System.out.println(cryptoModel.price);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        }); */

    }

    public void handleResponse(List<CryptoModel> modelList){
        this.modelList=new ArrayList<>(modelList);

        //RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter=new CryptoAdapter(this.modelList);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}