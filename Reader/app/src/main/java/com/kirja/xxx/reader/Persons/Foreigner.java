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
        String reverse = "";
        StringReverser sh = new StringReverser();
        if (!currentISBN.equals("0")) {
            if (XMLHandler.json != null && !XMLHandler.getResults().equals("0")) {
                s = XMLHandler.getAuthor();
                s = sh.getReversedName(s);
                s += " " + XMLHandler.getTitle();
                //Log.i("tekijä", s);
                Log.i("teos", s);
            }
            //Scanner sc = new Scanner(s);

        /*
        while (sc.hasNext()) {
            reverse += sh.translate(sc.next());
        }
        Log.i("translation", reverse);
        */


            XMLHandler.json = null;
        }
        if (currentISBN.equals("0")) s = "Tähän ei ole syötetty ISBN-tunnusta...Miksi?";
        s = s.trim();

        // translate result to "kontinkieli":
        for (String word : s.split("\\W+")) {
            reverse += sh.translate(word);
        }

        XMLHandler.json = null;
        Log.i("translation", reverse);
        tts.speak(reverse, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void chat() {
        String s = "anna kirja";
        //Scanner sc = new Scanner(s);
        StringReverser sh = new StringReverser();
        String reverse = "";

        for (String word : s.split("\\W+")) {
            reverse += sh.translate(word);
        }
        /*
        while (sc.hasNext()) {
            String word = sc.next();
            reverse += sh.translate(word);
        }
        */

        Log.i("reverse", reverse);
        if (!tts.isSpeaking()) tts.speak(reverse, TextToSpeech.QUEUE_FLUSH, null);
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
