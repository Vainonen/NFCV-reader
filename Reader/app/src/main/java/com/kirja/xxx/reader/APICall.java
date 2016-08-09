package com.kirja.xxx.reader;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kirja.xxx.reader.Persons.Person;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICall {

    Context context;

    public void getData(Context c, String isbn) {
        //String url = "https://www.googleapis.com/books/v1/volumes?q=ISBN:" + isbn;
        //https://api.finna.fi/v1/search?lookfor=9789520110727&field[]=fullRecord
        //String url = "https://api.finna.fi/v1/search?lookfor=" + isbn;
        String url = "https://api.finna.fi/v1/search?lookfor="+ isbn +"&field[]=fullRecord";
        context = c;

        if (!isConnected())
            Toast.makeText(context,
                    "Verkkoyhteys ei toimi!", Toast.LENGTH_LONG).show();
        else
            new HttpAsyncTask().execute(url);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setConnectTimeout (1000);
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                if (inputStream != null) {
                    String line = "";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = bufferedReader.readLine()) != null) result += line;
                } else result = "error";
                inputStream.close();
                connection.disconnect();
            } catch (Exception e) {
                Log.e("Error", e.getLocalizedMessage());
            }

            return result;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            XMLHandler xh = new XMLHandler(result);
            MainActivity.person.speak();
        }
    }
}