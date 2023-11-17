package com.example.cryptocurrency;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CryptoCurrencyViewHolder extends RecyclerView.ViewHolder {
    public TextView titleText, symbolText,amountText, percentageText;
    public ImageView currencyImage;


    public CryptoCurrencyViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.titleText);
        symbolText = itemView.findViewById(R.id.symbolText);
        currencyImage = itemView.findViewById(R.id.currencyImage);
        amountText = itemView.findViewById(R.id.amountText);
        percentageText = itemView.findViewById(R.id.percentageText);

    }
}
