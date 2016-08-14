package com.kirja.xxx.reader;

import android.util.Log;

public class StringReverser {

    //public StringReverser(String s) {result = s;}

    //Reverses first name and surname to correct order
    public String getReversedName(String name) {
        StringBuilder firstName = new StringBuilder("");
        StringBuilder surname = new StringBuilder("");
        boolean pointer = false; //indicates when reading first name
        for (int i = 0; i < name.length()-1; i++) {
            char c = name.charAt(i);
            if (c == ',') pointer = true;
            if (pointer && Character.isLetter(c)) firstName.append(c);
            else surname.append(c);
        }
        return firstName.toString() + " " + surname.toString();
    }

    //Translates String to language called kontinkieli
    public String translate(String word) {
        word.toLowerCase();
        if (word.equals(" ")) return "";
        int pointer = 0;
        String first = "ko";
        String last = "";
        char c;
        boolean vowel = false; // detects the first vowel
        Log.i("word", word);
        while (pointer < word.length() && !vowel) {
            c = word.charAt(pointer);
            if ("aeiouyåäö".contains(String.valueOf(c))) {
                if (pointer < word.length()-1 && c == word.charAt(pointer + 1)) {
                    Log.i("word", "tuplavokaali");
                    first += "o";
                    pointer++;
                }
                vowel = true;
            }
            else {
                if (vowel) first += word.substring(0, pointer);
            }
            last += c;
            pointer++;
        }
        first += word.substring(pointer, word.length());
        last += "ntti ";
        return first + " " + last;
    }
}