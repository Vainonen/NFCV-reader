package com.kirja.xxx.reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.kirja.xxx.reader.Persons.Fan;
import com.kirja.xxx.reader.Persons.Foreigner;
import com.kirja.xxx.reader.Persons.Person;
import com.kirja.xxx.reader.Persons.Teenager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static JSONHandler jh = null;
    public static XMLHandler xh = null;
    public static Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiobuttons);
        rg.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        Button[] rb = new Button[3];
        for (int i = 0; i < rb.length; i++) {
            rb[i] = new Button(this);
            rg.addView(rb[i]);
            rb[i].setOnClickListener(this);
            rb[i].setId(i);
            rb[i].setTextSize(30);
        }
        rb[0].setText("Teini");
        rb[1].setText("Kontinkielinen");
        rb[2].setText("Romantikko");
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        NOT NEEDED?
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        if (person != null) person.shutUp();
    }

    @Override
    public void onClick(View v) {
        String id = Integer.toString(v.getId());
        switch (id) {
            case "0": {
                person = new Teenager(this.getApplicationContext());
                break;
            }
            case "1": {
                person= new Foreigner(this.getApplicationContext());
                break;
            }
            case "2": {
                person = new Fan(this.getApplicationContext());
                break;
            }
        }
        Intent intent = new Intent(this, TagReader.class);
        startActivity(intent);
    }
}