package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.speech.tts.TextToSpeech;

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
        float speed = 1;
        float pitch = 1;
        tts.setSpeechRate(speed / 2);
        tts.setPitch(pitch / 2);
    }

    @Override
    public void speak() {
        String s = "";
        if (JSONHandler.json != null) s = JSONHandler.getTitle();
        JSONHandler.json = null;
        //tts.stop();
        if (!s.equals("Steel, Danielle")) s = "Tämä ei ole Danielle Steel";
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void chat() {
        String s = "";
        if (counter == 0) {
            s = "Tuokaa minulle Danielle Steel";
            counter++;
        }
        else {
            s = "Missä viipyy Danielle Steel";
            counter = 0;
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