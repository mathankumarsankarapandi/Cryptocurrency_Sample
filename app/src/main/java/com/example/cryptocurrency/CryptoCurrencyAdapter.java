package com.example.cryptocurrency;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyViewHolder>{
    private static CryptoCurrencyAdapter mAddressBookAdapterInstance;
    private Context context;
    List<CryptoCurrency> cryptoCurrencyList;
    private LayoutInflater inflater;

    public CryptoCurrencyAdapter(Context context, List<CryptoCurrency> cryptoCurrencyList) {
        try {
            this.context = context;
            this.cryptoCurrencyList = cryptoCurrencyList;
            inflater = LayoutInflater.from(this.context);
        } catch (Exception exception) {
        }
    }


    public static CryptoCurrencyAdapter getmAddressBookAdapterInstance(Context context, List<CryptoCurrency> cryptoCurrencyList) {
        try {
             if (mAddressBookAdapterInstance == null) {
                mAddressBookAdapterInstance = new CryptoCurrencyAdapter(context, cryptoCurrencyList);
             }
            return mAddressBookAdapterInstance;
        } catch (Exception exception) {
            return mAddressBookAdapterInstance;
        }
    }
    @NonNull
    @Override
    public CryptoCurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View view = inflater.inflate(R.layout.cryptocurrency_view_layout, parent, false);
            return new CryptoCurrencyViewHolder(view);
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoCurrencyViewHolder holder, int position) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyList.get(position);
        holder.titleText.setText(cryptoCurrency.getName());
        holder.titleText.setTextColor(Color.BLACK);
        holder.symbolText.setText(cryptoCurrency.getSymbol());
        holder.symbolText.setTextColor(Color.GRAY);

        holder.currencyImage.setImageBitmap(cryptoCurrency.getBitmap());
        holder.percentageText.setText(reformatNumber(cryptoCurrency.getTwentyFourHourPercentage())+"%");
        if(Double.parseDouble(cryptoCurrency.getTwentyFourHourPercentage()) > 0){
            holder.percentageText.setTextColor(Color.GREEN);
        } else {
            holder.percentageText.setTextColor(Color.RED);
        }
        holder.amountText.setText("$"+reformatNumber(cryptoCurrency.getAmount())+" USD");
        holder.amountText.setTextColor(Color.BLACK);

    }

    private String reformatNumber(String number) {
        try {
            return new DecimalFormat("##.##").format(Double.parseDouble(number)).toString();

        } catch (NumberFormatException e) {
            return number;
        }
    }

    public void setCryptoCurrencyList(List<CryptoCurrency> cryptoCurrencyList) {
        this.cryptoCurrencyList = cryptoCurrencyList;
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencyList.size();
    }
}
