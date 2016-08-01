 package com.kirja.xxx.reader;

import java.util.Scanner;

public class StringReverser {

    String result;

    public StringReverser(String s) {
        result = s;
    }

    //Reverses first name and surname to correct order
    public String getReversedName() {
        StringBuilder firstName = new StringBuilder("");
        StringBuilder surname = new StringBuilder("");
        boolean pointer = false; //indicates when reading first name
        for (int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            if (result.charAt(i) == ',') pointer = true;
            if (pointer) firstName.append(c);
            else surname.append(c);
        }
        return firstName.toString() + surname.toString();
    }

    //Translates String to language called kontinkieli
    public String translate(String word) {
        word.toLowerCase();
        int pointer = 0;
        String first = "ko";
        String last = "";
        char c;
        while (pointer < word.length()) {
            c = word.charAt(pointer);
            if ("aeiouyåäö".contains(String.valueOf(c))) {
                if (pointer < word.length() && c == word.charAt(pointer + 1)) {
                    last += c;
                    first += "o";
                }
                last += c;
                first += word.substring(0, pointer);
                pointer++;
            }
        }
        last += "ntti";
        return first + " " + last;
    }
}
    /*

    //Scanner scanner = new Scanner(s);
    for (int i = 0; i < ; i++)
    // /while (scanner.) {
        String s = scanner.next();

        if (!firstName.
                .isEmpty()) {
            if (s.equals(",") firstname.append(scanner.next());
            else surname.append(s);
        }
        else firstname.append(s);
    StringBuilder firstName = new StringBuilder();
StringBuilder surname = new StringBuilder();
Scanner scanner = new Scanner(String s);
while (scanner.hasNext()) {
 String s = scanner.next();
  if (!firstName.isEmpty()) {
   if (s.equals(",") {
  while (scanner.hasNext()) {
   surname.append(scanner.next());
  }
 }
 firstname.append(scanner.next());
}
}
String name = firstName.toString() + surname.toString();

ArrayList <String> words;
boolean vowel = false;
if (lang = fi)
StringBuilder first = new StringBuilder();
StringBuilder last = new StringBuilder();
String s, next, first, last;
s = s.toLowerCase();
first.append("k");
while (scanner.hasNext()) {
if (!"aeiouyåäö".contains(next) && !vowel)
 String next = scanner.next();
 if ("aeiouyåäö".contains(next)) {
  last.append(next);
  vowel = true;
 if (!vowel) {
 if (!"aeiouyåäö".contains(next) && vowel) {

last.append("ntti");
*/

