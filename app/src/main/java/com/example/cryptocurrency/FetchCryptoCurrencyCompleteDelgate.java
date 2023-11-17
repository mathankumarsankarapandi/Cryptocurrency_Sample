package com.example.cryptocurrency;

import java.util.List;

public interface FetchCryptoCurrencyCompleteDelgate {
    void onTaskCompleted(List<CryptoCurrency> cryptoCurrencies);
}
