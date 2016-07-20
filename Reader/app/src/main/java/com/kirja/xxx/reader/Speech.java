package com.kirja.xxx.reader;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class Speech implements TextToSpeech.OnInitListener {
    TextToSpeech tts;

    public Speech(Context context) {
        tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR){
            tts.setLanguage(new Locale("fi"));
        }
    }
    public void talk(String text) {
        /*
        float speed = 1;
        float pitch = 1;
        t1.setSpeechRate(speed);
        t1.setPitch(pitch);
        */
        //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
/*
    public void shutUp() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
    */
}