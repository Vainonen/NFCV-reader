package com.kirja.xxx.reader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHandler {

    public static JSONObject json;
    public static JSONArray records;
    public static String results;

    public JSONHandler(String result) {
        Log.i("result", result);
        try {
            json = new JSONObject(result);
            results = json.getString("resultCount");
            records = json.getJSONArray("records");
        } catch (Exception e) {
            Log.e("JSON error", e.toString());
        }
    }

    //TODO: returns just the first author name in case of more authors in one title
    public static String getAuthor() {
        try {
            JSONArray authors = records.getJSONObject(0).getJSONArray("nonPresenterAuthors");
            return authors.getJSONObject(0).getString("name");
        } catch (Exception e) {
            Log.e("JSON error", e.toString());
        }
        return ""; //returns empty String for Speech class
    }

    public static String getTitle() {
        try {
            return records.getJSONObject(0).getString("title");
        } catch (Exception e) {
            Log.e("JSON error", e.toString());
        }
        return ""; //returns empty String for Speech class
    }

    //TODO:
    public static String getPageNumber() {
        return null;
    }

    public static String getLanguage() {
        try {
            JSONArray languages = records.getJSONObject(0).getJSONArray("languages");
            return languages.getString(0);
        } catch (Exception e) {
            Log.e("JSON error", e.toString());
        }
        return ""; //returns empty String for Speech class
    }

    public static String getResults() {
        return results;
    }
}
