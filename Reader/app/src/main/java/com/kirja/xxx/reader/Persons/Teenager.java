package com.kirja.xxx.reader.Persons;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;

import java.util.Locale;

public class Teenager implements Person {

    TextToSpeech tts;

    public Teenager(Context context) {
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
        tts.setSpeechRate(speed);
        tts.setPitch(pitch);
    }

    @Override
    public void speak() {
        String s = "";
        if (JSONHandler.json != null) s = JSONHandler.getTitle();
        JSONHandler.json = null;
        //tts.stop();
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
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
    public boolean speaking() {
        if (tts.isSpeaking()) return true;
        return false;
    }
}