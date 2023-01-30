package com.ramazantiftik.cryptocurrencyretrofit.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramazantiftik.cryptocurrencyretrofit.databinding.RecyclerRowBinding;
import com.ramazantiftik.cryptocurrencyretrofit.model.CryptoModel;

import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoHolder> {

    private ArrayList<CryptoModel> modelList;
    private String[] colors={"#8080e0","#f2bcba","#db781b","#5ec8d8","#8c7d20","#ff33ff","#6c1491","#9b334f"};
    public CryptoAdapter(ArrayList<CryptoModel> modelList){
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public CryptoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CryptoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoHolder holder, int position) {
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position%8]));
        holder.binding.currencyText.setText(modelList.get(position).currency);
        holder.binding.priceText.setText(modelList.get(position).price);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class CryptoHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding binding;

        public CryptoHolder(@NonNull RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
