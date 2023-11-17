package com.example.cryptocurrency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements FetchCryptoCurrencyCompleteDelgate {
    RecyclerView currencyRecyclerView;
    ProgressBar progressBar;
    LinearLayout currencyLayout;

    EditText searchEditText;
    CryptoCurrencyAdapter cryptoCurrencyAdapter;
    List<CryptoCurrency> cryptoCurrencies;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencyRecyclerView = findViewById(R.id.currencyRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        currencyLayout = findViewById(R.id.currencyLayout);
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<CryptoCurrency> cryptoCurrenciesList = new ArrayList<>();
                if(searchEditText.getText().toString().trim().length() > 0){
                    for(CryptoCurrency cryptoCurrency : cryptoCurrencies){
                        if(cryptoCurrency.getName().toLowerCase(Locale.ROOT).contains(searchEditText.getText().toString().trim().toLowerCase(Locale.ROOT)) ||
                                cryptoCurrency.getAmount().toLowerCase(Locale.ROOT).contains(searchEditText.getText().toString().trim().toLowerCase(Locale.ROOT)) ||
                                cryptoCurrency.getSymbol().toLowerCase(Locale.ROOT).contains(searchEditText.getText().toString().trim().toLowerCase(Locale.ROOT)) ||
                                cryptoCurrency.getTwentyFourHourPercentage().toLowerCase(Locale.ROOT).contains(searchEditText.getText().toString().trim().toLowerCase(Locale.ROOT))){
                                cryptoCurrenciesList.add(cryptoCurrency);
                        }
                    }
                } else {
                    cryptoCurrenciesList.addAll(cryptoCurrencies);
                }
                cryptoCurrencyAdapter.setCryptoCurrencyList(cryptoCurrenciesList);
                cryptoCurrencyAdapter.notifyDataSetChanged();
            }
        });
        FetchCryptoCurrencyData fetchCryptoCurrencyData = new FetchCryptoCurrencyData(this,this, this);
        fetchCryptoCurrencyData.execute();

    }


    @Override
    public void onTaskCompleted(List<CryptoCurrency> cryptoCurrencies) {
        if (cryptoCurrencies.size() > 0) {
            this.cryptoCurrencies = cryptoCurrencies;
            progressBar.setVisibility(View.GONE);
            cryptoCurrencyAdapter = CryptoCurrencyAdapter.getmAddressBookAdapterInstance(this, cryptoCurrencies);
            currencyRecyclerView.setAdapter(cryptoCurrencyAdapter);
            currencyLayout.setVisibility(View.VISIBLE);
        }
    }
}

