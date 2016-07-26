package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;

import java.util.Locale;

public class Fan implements Person {

    TextToSpeech tts;
    int counter = 0;

    public Fan(Context context) {

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("fi"));
                    float speed = 0.5f;
                    float pitch = 0.5f;
                    tts.setSpeechRate(speed);
                    tts.setPitch(pitch);
                }
            }
        });
    }

    @Override
    public void speak() {
        String s = "";
        if (JSONHandler.json != null) s = JSONHandler.getTitle();
        JSONHandler.json = null;
        //tts.stop();
        tts.setSpeechRate(0.5f);
        if (s.equals("Steel, Danielle")) s = "Tämä ei ole Daniel le Steell";
        else {
            s = "aaaaaaaaaaaaaaaaaaah!";
            tts.setSpeechRate(1.0f);
            tts.setPitch(2.0f);
        }
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        tts.setPitch(0.5f);
    }

    @Override
    public void chat() {
        String s = "";
        if (counter == 0) {
            s = "Tuokaa minulle. Daniel le Steell";
            counter++;
            tts.setSpeechRate(0.5f);
            Log.i("order", "eka");
        }
        else {
            s = "Missä viipyy        Danielle Steel?";
            Log.i("order", "toka");
            counter = 0;
            tts.setSpeechRate(1);
        }

        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
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
        //not necessary property for this class
    }
}