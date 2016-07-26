package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;
import com.kirja.xxx.reader.StringReverser;

import java.util.ArrayList;
import java.util.Locale;

public class Teenager implements Person {

    TextToSpeech tts;
    ArrayList <String> books;
    String currenISBN;

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
                }
            }
        });
    }

    @Override
    public void speak() {
        String s = "";
        String language = "";

        if (JSONHandler.json != null) {
            s = JSONHandler.getAuthor();
            StringReverser sh = new StringReverser(s);
            s = sh.getReversedName();
            s += JSONHandler.getTitle();
        }
        JSONHandler.json = null;
        try {
            language = JSONHandler.getLanguage();
            tts.setLanguage(new Locale(language));
        }
        catch (Exception e) {
            Log.e("error", "language not found");
        }
        if (books.contains(currenISBN)) s = "Olen lukenut jo tämän kursorisesti.";
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        tts.setLanguage(new Locale("fi"));
        books.add(currenISBN);
    }

    public void chat() {
        String s = "Anna jotain luettavaa";
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
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
        currenISBN = isbn;
    }
}