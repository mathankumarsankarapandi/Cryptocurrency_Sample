package com.example.cryptocurrency;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FetchCryptoCurrencyData extends AsyncTask<Void, Void, List<CryptoCurrency>> {

    private final OkHttpClient okHttpClient;
    FetchCryptoCurrencyCompleteDelgate fetchCryptoCurrencyCompleteDelgate;
    Activity activity;
    Context context;
    FetchCryptoCurrencyData(Activity activity, Context context ,FetchCryptoCurrencyCompleteDelgate fetchCryptoCurrencyCompleteDelgate){
        this.fetchCryptoCurrencyCompleteDelgate = fetchCryptoCurrencyCompleteDelgate;
        this.context = context;
        this.activity = activity;
      okHttpClient = new OkHttpClient.Builder()
              .followRedirects(true)
              .followSslRedirects(true)
              .connectTimeout(30,TimeUnit.MINUTES)
              .readTimeout(30, TimeUnit.MINUTES)
              .writeTimeout(50, TimeUnit.MINUTES)
              .callTimeout(30, TimeUnit.MINUTES)
              .retryOnConnectionFailure(false)
              .build();
  }
    @Override
    protected List<CryptoCurrency> doInBackground(Void... voids) {
        return PullPersons();
    }

    @Override
    protected void onPostExecute(List<CryptoCurrency> cryptoCurrencies) {
        fetchCryptoCurrencyCompleteDelgate.onTaskCompleted(cryptoCurrencies);
        super.onPostExecute(cryptoCurrencies);
    }

    public  List<CryptoCurrency> PullPersons() {
        URL url = null;
        String data = null;
        ResponseBody responseBody = null;
        try {
            url = new URL("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=20&sort=market_cap");
            Request request = new Request.Builder().get().url(url).addHeader("X-CMC_PRO_API_KEY","b0f08585-d4d7-4f21-8a0e-fcab9059381e").build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            data = response.body().string();

            responseBody = response.body();
            if( responseBody!= null) {
                responseBody.close();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            Log.i("Exceptionb", fileNotFoundException.toString());
        } catch (SocketTimeoutException socketTimeoutException) {
            Log.i("Exceptionb", socketTimeoutException.toString());

        } catch (MalformedURLException malformedURLException) {
            Log.i("Exceptionb", malformedURLException.toString());

        } catch (IOException ioException) {
            Log.i("Exceptionb", ioException.toString());

        } finally {
            Log.i("Exceptionb","finally");
        }

        JSONObject personsRaw = null;
        try {
            personsRaw = new JSONObject(data);

        } catch (JSONException jsonException) {
            Log.i("Exceptionb", jsonException.toString());

        }
        return storePersons(personsRaw);
    }

    private List<CryptoCurrency>  storePersons(JSONObject personsRaw) {
        String name;
        String symbol;
        String price;
        String percentage;
        String id;
        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();
        List<String> iDList = new ArrayList<>();
        try {
            JSONArray allPersons = personsRaw.getJSONArray("data");
            if (allPersons != null) {
                int count = allPersons.length();
                for (int i = 0; i < count; i++) {

                    JSONObject singlePerson = allPersons.getJSONObject(i);
                    id = singlePerson.getString("id");
                    name = singlePerson.getString("name");
                    symbol = singlePerson.getString("symbol");
                    JSONObject singlePerson1 = singlePerson.getJSONObject("quote").getJSONObject("USD");
                    price = singlePerson1.getString("price");
                    percentage = singlePerson1.getString("percent_change_24h");
                    iDList.add(id);
                    CryptoCurrency cryptoCurrency = new CryptoCurrency();
                    cryptoCurrency.setName(name);
                    cryptoCurrency.setId(id);
                    cryptoCurrency.setSymbol(symbol);
                    cryptoCurrency.setAmount(price);
                    cryptoCurrency.setTwentyFourHourPercentage(percentage);
                    cryptoCurrencyList.add(cryptoCurrency);
                }
            }
            try {

                cryptoCurrencyList = FetchLogo(iDList,cryptoCurrencyList);
            } catch (Exception exception) {

            }

        } catch (JSONException jsonException) {

        }
        return cryptoCurrencyList;
    }

    public  List<CryptoCurrency> FetchLogo(List<String> iDList, List<CryptoCurrency> cryptoCurrencyList) {
        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < iDList.size(); x++) {
            builder.append(iDList.get(x));

            if (x < (iDList.size() - 1))
                builder.append(",");

        }
        URL url = null;
        String data = null;
        ResponseBody responseBody = null;
        try {
            url = new URL("https://pro-api.coinmarketcap.com/v2/cryptocurrency/info?id="+builder.toString());
            Request request = new Request.Builder().get().url(url).addHeader("X-CMC_PRO_API_KEY","b0f08585-d4d7-4f21-8a0e-fcab9059381e").build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            data = response.body().string();

            responseBody = response.body();
            if( responseBody!= null) {
                responseBody.close();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            Log.i("Exceptionb", fileNotFoundException.toString());
        } catch (SocketTimeoutException socketTimeoutException) {
            Log.i("Exceptionb", socketTimeoutException.toString());

        } catch (MalformedURLException malformedURLException) {
            Log.i("Exceptionb", malformedURLException.toString());

        } catch (IOException ioException) {
            Log.i("Exceptionb", ioException.toString());

        } finally {
            Log.i("Exceptionb","finally");
        }

        JSONObject personsRaw = null;
        try {
            personsRaw = new JSONObject(data);

        } catch (JSONException jsonException) {
            Log.i("Exceptionb", jsonException.toString());

        }
        String logo;
        try {
            JSONObject jsonObject = personsRaw.getJSONObject("data");
            int count = 0;
            for (String idValue : iDList) {
                JSONObject jsonObject1 = jsonObject.getJSONObject(idValue);
                logo = jsonObject1.getString("logo");

                CryptoCurrency cryptoCurrency = cryptoCurrencyList.get(count);
                if(idValue.equalsIgnoreCase(cryptoCurrency.getId())){
                    cryptoCurrency.setLogo(logo);
                    Bitmap bitmap = downloadFromUrl(logo,context,activity );
                    cryptoCurrency.setBitmap(bitmap);
                }
                count++;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return cryptoCurrencyList;
    }

    public Bitmap downloadFromUrl(String imageURL, Context context, Activity activity) {  //this is the downloader method
        try {
                File file = new File(context.getFilesDir(), imageURL.split("/")[imageURL.split("/").length-1]);
                URL url = new URL(imageURL);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                InputStream input = urlConnection.getInputStream();
                OutputStream output = new FileOutputStream(file);

                byte[] buffer = new byte[50];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                    output.write(buffer, 0, bytesRead);
                }
                output.close();
                input.close();
                if (file.exists()) {


                            final Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                   return Bitmap.createScaledBitmap(myBitmap, 450, 250, false);
                }


        } catch (MalformedURLException malformedURLException) {

        } catch (IOException ioException) {

        }
return  null;
    }



}

