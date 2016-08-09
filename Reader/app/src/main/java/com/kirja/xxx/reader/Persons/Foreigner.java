package com.kirja.xxx.reader.Persons;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kirja.xxx.reader.StringReverser;
import com.kirja.xxx.reader.XMLHandler;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Foreigner implements Person {

    TextToSpeech tts;
    String currentISBN;

    public Foreigner (Context context) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("fi"));
                    float speed = 1.0f;
                    float pitch = 1.0f;
                    tts.setSpeechRate(speed);
                    tts.setPitch(pitch);
                    chat();
                }
            }
        });
    }

    @Override
    public void speak() {
        String s = "";

        if (XMLHandler.json != null) {
            s = XMLHandler.getAuthor();

            StringReverser sh = new StringReverser(s);
            s = sh.getReversedName();
            Log.i("tekijä", s);
            s += "..." + XMLHandler.getTitle();
            Log.i("teos", s);
        }
        Scanner sc = new Scanner(s);
        StringReverser sh = new StringReverser(s);
        String reverse = "";
        while (sc.hasNext()) {
            reverse += sh.translate(sc.next());
        }
        Log.i("translation", reverse);
        XMLHandler.json = null;
        tts.speak(reverse, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void chat() {
        String s = "anna kirja";
        Scanner sc = new Scanner(s);
        StringReverser sh = new StringReverser(s);
        String reverse = "";

        while (sc.hasNext()) {
            String word = sc.next();
            reverse += sh.translate(word);
        }
        Log.i("reverse", reverse);
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
