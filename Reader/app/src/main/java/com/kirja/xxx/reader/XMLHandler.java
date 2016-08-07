package com.kirja.xxx.reader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.Scanner;

public class XMLHandler {

    public static JSONObject json;
    public static JSONArray records;
    public static String results;
    public static String xml;

    public XMLHandler(String result) {
        Log.i("result", result);
        try {
            json = new JSONObject(result);
            results = json.getString("resultCount");
            records = json.getJSONArray("records");
            xml = records.getJSONObject(0).getString("fullRecord");
            Log.i("xml", xml);
            int number = readXML();
            Log.i("sivumäärä", Integer.toString(number));
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

    private int readXML() {
        int pages = 0;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xml));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    try {
                        String name = xpp.getName();
                        String value = xpp.getAttributeValue(0);
                        if (name.equals("datafield") && value.equals("300")) {
                            while (eventType != XmlPullParser.END_TAG) {
                                eventType = xpp.next();
                                if (eventType == XmlPullParser.START_TAG) {
                                    name = xpp.getName();
                                    value = xpp.getAttributeValue(0);
                                    if (name.equals("subfield") && value.equals("a")) {
                                         eventType = xpp.next();
                                        if (eventType == XmlPullParser.TEXT) {
                                            String marcpages = xpp.getText();
                                            Scanner sc = new Scanner(marcpages);
                                            try {
                                                pages = sc.nextInt();
                                            } catch (Exception e) {
                                                Log.e("error", "number not in correct format");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("xml error", "attribute not found");
                    }
                    eventType = xpp.next();
                }
            }
        }
        catch (Exception e) {
            Log.e("readerror", e.toString());
        }
        return pages;
    }


