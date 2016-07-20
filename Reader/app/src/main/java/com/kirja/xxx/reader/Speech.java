package com.kirja.xxx.reader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class Speech extends Activity {
//public class Speech implements TextToSpeech.OnInitListener {
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*public Speech(Context context) {
        tts = new TextToSpeech(context, this);
    }
    */

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale("fi"));
                }
            }
        });
/*
        @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR){
            tts.setLanguage(new Locale("fi"));
        }
        float speed = 1;
        float pitch = 1;
        tts.setSpeechRate(speed);
        tts.setPitch(pitch);
    }
*/
    }

    public void talk(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        Log.i("puhe", text);
    }

    public void talkResult(JSONHandler jh) {
        String puhe = jh.getTitle();
        Log.i("puhe", puhe);
        //tts.speak(puhe, TextToSpeech.QUEUE_FLUSH, null);
        tts.speak(jh.getTitle(), TextToSpeech.QUEUE_FLUSH, null);
    }

    public String getID() {
        return tts.toString();
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

}