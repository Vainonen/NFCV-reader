package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;

import java.util.Locale;

public class Fan implements Person {

    TextToSpeech tts;
    int counter;

    public Fan(Context context) {
        counter = 0;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale("fi"));
                }
            }
        });

        float pitch = 1;
        tts.setPitch(pitch / 2);
    }

    @Override
    public void speak() {
        String s = "";
        if (JSONHandler.json != null) s = JSONHandler.getTitle();
        JSONHandler.json = null;
        //tts.stop();
        tts.setSpeechRate(1 / 2);
        if (s.equals("Steel, Danielle")) s = "Tämä ei ole Daniel le Steell";
        else {
            s = "aaaaaaaaah!";
            tts.setSpeechRate(1 / (2 / 3));
            tts.setPitch(1 / (2 / 3));
        }
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        tts.setPitch(1 / 2);
    }

    @Override
    public void chat() {
        String s = "";
        if (counter == 0) {
            s = "Tuokaa minulle. Daniel le Steell";
            counter++;
            tts.setSpeechRate(1 / 2);
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
    public boolean speaking() {
        if (tts.isSpeaking()) return true;
        return false;
    }
}