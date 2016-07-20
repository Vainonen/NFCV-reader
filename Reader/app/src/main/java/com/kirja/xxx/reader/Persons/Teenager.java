package com.kirja.xxx.reader.Persons;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.JSONHandler;
import com.kirja.xxx.reader.TagReader;

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
    }

    @Override
    public void speak() {
        float speed = 1;
        float pitch = 1;
        tts.setSpeechRate(speed);
        tts.setPitch(pitch);
        String s = "";
        if (JSONHandler.json != null) s = JSONHandler.getTitle();
        JSONHandler.json = null;
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        Log.i("puhe", s);
    }

    public void chat() {
        float speed = 1;
        float pitch = 1;
        tts.setSpeechRate(speed);
        tts.setPitch(pitch);
        String s = "Anna jotain luettavaa";
        tts.speak(s, TextToSpeech.QUEUE_ADD, null);
        Log.i("puhe", "chat");
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
}