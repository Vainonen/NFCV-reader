package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;
import com.kirja.xxx.reader.XMLHandler;

import java.util.Locale;

public class Fan implements Person {

    TextToSpeech tts;
    int counter = 0;
    private String currentISBN;

    public Fan(Context context) {

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("fi"));
                    float pitch = 0.5f;
                    tts.setPitch(pitch);
                    chat();
                }
            }
        });
    }

    @Override
    public void speak() {
        String s = "";
        if (XMLHandler.json != null) s = JSONHandler.getTitle();
        XMLHandler.json = null;

        tts.setSpeechRate(0.5f);

        if (!s.equals("Steel, Danielle")) s = "Tämä ei ole Daniel le Steell";
        if (currentISBN.equals(0)) s = "Tähän ei ole syötetty ISBN-tunnusta...Miksi?";
        if (XMLHandler.getResults().equals("0")) s = "ISBN-tunnusta ei löytynyt.";
        if (s.equals("Steel, Danielle")) {
            s = "aaaaaaaaaaaaaaaaaaah!";
            tts.setSpeechRate(0.1f);
            tts.setPitch(2.0f);
        }

        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        tts.setPitch(0.5f);
        }

    @Override
    public void chat() {
        String s = "";
        switch (counter) {
            case (0): {
                s = "Tuokaa minulle Daniel le Steell";
                tts.setSpeechRate(0.5f);
                break;
            }
            case (1): {
                s = "Missä viipyy... Danielle Steel?";
                tts.setSpeechRate(1);
                break;
            }
            case (2): {
                s = "Minä haluan Daniel le Steelin";
                tts.setSpeechRate(1.5f);
                break;
            }
        }
        counter++;
        if (counter > 2) counter = 0;
        tts.speak(s, TextToSpeech.QUEUE_ADD, null);
    }

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