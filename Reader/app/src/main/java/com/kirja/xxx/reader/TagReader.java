package com.kirja.xxx.reader;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TagReader extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    TextView textViewInfo, textViewTagInfo;
    LinearLayout linearLayout;
    Speech speech;
    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInfo = (TextView) findViewById(R.id.info);
        textViewTagInfo = (TextView) findViewById(R.id.info);
        linearLayout = (LinearLayout) findViewById(R.id.data);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        speech = new Speech(this.getApplicationContext());

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "Kytke laitteen NFC-toiminto päälle!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Toast.makeText(this,
                    "RFID löytyi",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                textViewInfo.setText("tag == null");
            } else {
                getData(tag);
            }

        } else {
            Toast.makeText(this,
                    action,
                    Toast.LENGTH_SHORT).show();
        }

        executorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                speech.talk("minä puhun suomea");
                Log.i("talk", "not");
            }
        }, 0, 20, TimeUnit.SECONDS);

    }

/*
    public void onPause(){
        speech.shutUp();
        super.onPause();
        executorService.shutdown();
    }
*/
    private void getData(Tag tag) {
        NfcV nfcv = NfcV.get(tag);

        try {
            nfcv.connect();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Yhteys ei toimi!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Read first 8 blocks containing the 32 byte of data
            byte [] cmd = new byte[]{
                    (byte) 0x00, // Flags
                    (byte) 0x23, // Command: Read multiple blocks
                    (byte) 0x00, // First block (offset)
                    (byte) 0x08  // Number of blocks
            };
            byte[] userdata = nfcv.transceive(cmd);
            Log.i("bytes", Integer.toString(userdata.length));
            // ISBN location on the NFCV tag:
            userdata = Arrays.copyOfRange(userdata, 24, 30);
            String isbn = new BigInteger(userdata).toString();
            TextView tv = new TextView(this);
            tv.setText(isbn);
            linearLayout.addView(tv);
            APICall ac = new APICall();
            ac.getData(this.getApplicationContext(), isbn);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Lukeminen epäonnistui!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            nfcv.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Yhteyttä ei voitu sulkea!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}