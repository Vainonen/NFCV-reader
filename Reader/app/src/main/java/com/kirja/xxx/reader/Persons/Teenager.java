package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;
import com.kirja.xxx.reader.StringReverser;
import com.kirja.xxx.reader.XMLHandler;

import java.util.ArrayList;
import java.util.Locale;

public class Teenager implements Person {

    TextToSpeech tts;
    ArrayList <String> books;
    String currentISBN;

    public Teenager(Context context) {
        books = new ArrayList();
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("fi"));
                    float speed = 1.3f;
                    float pitch = 1.3f;
                    tts.setSpeechRate(speed);
                    tts.setPitch(pitch);
                    chat();
                }
            }
        });
    }

    @Override
    public void speak() {
        String s = "";
        String language = "";

        //if (XMLHandler.json != null && !XMLHandler.getResults().equals("0") && !currentISBN.equals("0")) {
        if (XMLHandler.json != null && !currentISBN.equals("0")) {
            s = XMLHandler.getAuthor();
            StringReverser sh = new StringReverser();
            s = sh.getReversedName(s);
            Log.i("tekijä", s);
            s += "..." + XMLHandler.getTitle();
            Log.i("teos", s);
            if (XMLHandler.getPageNumber() > 400) s += "liian paksu...ei vittu jaksa lukea";
            if (XMLHandler.getPageNumber() < 100) s += "just sopivan kokonen...kiitosh";
            if ((XMLHandler.getPageNumber() > 99) && (XMLHandler.getPageNumber() < 401)) s +="... Mikä vittu tää on";
            if (XMLHandler.getResults().equals("0")) s = "ISBN-tunnusta ei löytynyt.";
        }
        XMLHandler.json = null;
        /*
        try {
            language = JSONHandler.getLanguage();
            tts.setLanguage(new Locale(language));
        }
        catch (Exception e) {
            Log.e("error", "language not found");
        }
        */
        if (currentISBN.equals("0")) s = "Tähän ei ole syötetty ISBN-tunnusta...Miksi?";
        if (books.contains(currentISBN)) s = "Olen jo lukenut tämän kursorisesti.";

        Log.i("puhe", s);
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        //tts.setLanguage(new Locale("fi"));
        if (!currentISBN.equals("0")) books.add(currentISBN);
    }

    @Override
    public void chat() {
        String s = "Anna jotain luettavaa";
        if (!tts.isSpeaking()) tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }
/*
    @Override
    public void speakResult(JSONHandler jh) {
        float speed = 1;
        float pitch = 1;
        tts.setSpeechRate(speed);
        tts.setPitch(pitch);
        String speech = jh.getTitle();
        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        Log.i("puhe", speech);
    }
*/


    @Override
    public void shutUp() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void addISBN(String isbn) {
        currentISBN = isbn;
    }
}