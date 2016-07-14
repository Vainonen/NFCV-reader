package com.kirja.xxx.reader;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class Speech extends Activity {
    TextToSpeech t1;

    public Speech(Context context) {

        t1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void talk() {
        String toSpeak = "hei";
        float speed = 1;
        float pitch = 1;
        t1.setSpeechRate(speed);
        t1.setPitch(pitch);
        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
        t1.speak("hei", TextToSpeech.QUEUE_ADD, null);
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