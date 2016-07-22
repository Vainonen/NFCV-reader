package com.kirja.xxx.reader;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kirja.xxx.reader.Persons.Fan;
import com.kirja.xxx.reader.Persons.Person;
import com.kirja.xxx.reader.Persons.Teenager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    public static JSONHandler jh = null;
    public static Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person = new Fan(this.getApplicationContext());
        Intent intent = new Intent(this, TagReader.class);
        startActivity(intent);
    }
}