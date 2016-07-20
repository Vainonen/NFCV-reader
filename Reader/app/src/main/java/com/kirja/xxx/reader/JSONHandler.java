package com.kirja.xxx.reader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONHandler {

    JSONObject json;

    public JSONHandler(String result) {
        try {
            json = new JSONObject(result);
            JSONArray records = json.getJSONArray("records");
            JSONObject authorarray = new JSONObject(records.getJSONObject(0).getString("nonPresenterAuthors"));
            JSONArray authors = json.getJSONArray("nonPresenterAuthors");
            //String author = authorarray.getJSONObject(0).getString("name");
            String title = records.getJSONObject(0).getString("title");
            Log.i("title", title);
        }
        catch (Exception e){
            Log.e("JSON error", e.toString());
        }
    }
 // convert String to JSONObject
    //JSONArray industryIdentifiers = json.getJSONArray("industryIdentifiers"); // get articles array
    //industryIdentifiers.getJSONObject(0).getString("pageCount") // return an article url
}
