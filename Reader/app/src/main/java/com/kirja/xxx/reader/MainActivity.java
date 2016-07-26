package com.kirja.xxx.reader;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static JSONHandler jh = null;
    public static Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup rg = new RadioGroup(this);
        rg = (RadioGroup) findViewById(R.id.radiobuttons);
        rg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        rg.setOrientation(LinearLayout.VERTICAL);
        Button[] rb = new Button[2];
        for (int i = 0; i < rb.length; i++) {
            rb[i] = new Button(this);
            rg.addView(rb[i]);
            rb[i].setOnClickListener(this);
            rb[i].setId(i);
        }
        rb[0].setText("Teini");
        rb[1].setText("Romantikko");
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        NOT NEEDED?
    }
    */

    @Override
    public void onClick(View v) {
        String id = Integer.toString(v.getId());
        switch (id) {
            case "0": {
                person = new Teenager(this.getApplicationContext());
                break;
            }
            case "1": {
                person = new Fan(this.getApplicationContext());
                break;
            }
        }
        Intent intent = new Intent(this, TagReader.class);
        startActivity(intent);
    }
}