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
    public static XmlPullParser xpp;

    public XMLHandler(String result) {
        Log.i("result", result);
        try {
            json = new JSONObject(result);
            records = json.getJSONArray("records");
            results = json.getString("resultCount");
            xml = records.getJSONObject(0).getString("fullRecord");

            Log.i("xml", xml);

        } catch (Exception e) {
            Log.e("XML error", e.toString());
        }
    }

/*
    public static String getLanguage() {
        try {
            JSONArray languages = records.getJSONObject(0).getJSONArray("languages");
            return languages.getString(0);
        } catch (Exception e) {
            Log.e("JSON error", e.toString());
        }
        return ""; //returns empty String for Speech class
    }
*/

    //TODO: infinite loop if xml is malformed?
    private static String getDatafield(String number, String value) {



        String result = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xml));

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    try {
                        if (xpp.getName().equals("datafield") && xpp.getAttributeValue(0).equals(number)) {
                            result = getSubfield(value);
                        }
                    } catch (Exception e) {
                        Log.e("xml error", "attribute not found");
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            Log.e("xml error", "what?");
        }
        return result;
    }

    private static String getSubfield (String value) {
        String result = null;
        try {
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_TAG) {
                eventType = xpp.next();
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("subfield") && xpp.getAttributeValue(0).equals(value)) {
                        eventType = xpp.next();
                        if (eventType == XmlPullParser.TEXT) {
                            result = xpp.getText();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e ("xml error", e.toString());
        }
        return result;
    }

    public static int getPageNumber () {
        String result = getDatafield("300", "a");
        int pages = 0;
        Scanner sc = new Scanner(result);
        try {
            pages = sc.nextInt();
        } catch (Exception e) {
            Log.e("error", "number not in correct format");
        }
        return pages;
    }

    public static String getTitle () {
        return getDatafield("245", "a");
                //+ getDatafield("245", "b");
    }

    //also 700, a & 245, c:
    public static String getAuthor () {
        return getDatafield("100", "a");
    }

    public static String getResults() {
        return results;
    }
}