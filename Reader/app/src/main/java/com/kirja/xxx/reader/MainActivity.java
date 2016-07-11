package com.kirja.xxx.reader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    TextView textViewInfo, textViewTagInfo;
    LinearLayout linearLayout;

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

    }

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

            // Get system information (0x2B)
            byte[] cmd = new byte[] {
                    (byte)0x00, // Flags
                    (byte)0x2B // Command: Get system information
            };
            byte[] systeminfo = nfcv.transceive(cmd);

            // Chop off the initial 0x00 byte:
            systeminfo = Arrays.copyOfRange(systeminfo, 1, 15);
            systeminfo = Arrays.copyOfRange(systeminfo, 2, systeminfo.length - 1);

            for (String tech : tag.getTechList()) {
                // Read first 8 blocks containing the 32 byte of userdata defined in the Danish model
                cmd = new byte[]{
                        (byte) 0x00, // Flags
                        (byte) 0x23, // Command: Read multiple blocks
                        (byte) 0x00, // First block (offset)
                        (byte) 0x08  // Number of blocks
                };
                byte[] userdata = nfcv.transceive(cmd);

                // Chop off the initial 0x00 byte:
                userdata = Arrays.copyOfRange(userdata, 1, 32);

                for (byte info : userdata) {
                    String hex = bytesToHex(userdata);
                    Log.i("userdata", hex);
                    TextView tv = new TextView(this);
                    tv.setText(hex);
                    linearLayout.addView(tv);
                }
            }

            //textView1.setText(danishTag.Country());
            //textView2.setText(danishTag.ISIL());
            //textView3.setText(danishTag.Barcode());
            //textView33.setText("Del " + danishTag.getPartNum() + " av " + danishTag.getofParts());

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

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

/*
public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    TextView textViewInfo;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInfo = (TextView) findViewById(R.id.info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!",
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
                    "onResume()",
                    Toast.LENGTH_SHORT).show();
            Log.i("viesti", "löytyi");
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                textViewInfo.setText("tag == null");
                Log.i("viesti", "null");
            } else {
                String tagInfo = tag.toString() + "\n";

                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
                tagInfo += "length = " + tagId.length + "\n";
                for (int i = 0; i < tagId.length; i++) {
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
                }
                tagInfo += "\n";
                Log.i("viesti", "täällä");
                NdefMessage[] messages = null;
                Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                if (rawMessages != null) {
                    messages = new NdefMessage[rawMessages.length];
                    for (int i = 0; i < rawMessages.length; i++) {
                        messages[i] = (NdefMessage) rawMessages[i];
                    }
                }
                String result = "";
                if (messages[0] != null) {
                    byte[] payload = messages[0].getRecords()[0].getPayload();
                    // this assumes that we get back am SOH followed by host/code
                    for (int b = 0; b < payload.length; b++) { // skip SOH
                        result += (char) payload[b];
                    }
                Log.i("payload", Integer.toString(payload.length));
                }

                String[] techList = tag.getTechList();
                tagInfo += "\nTech List\n";
                tagInfo += "length = " + techList.length + "\n";
                for (int i = 0; i < techList.length; i++) {
                    tagInfo += techList[i] + "\n ";
                }

                textViewInfo.setText(tagInfo);
            }
            } else {
                    Toast.makeText(this,
                            "onResume() : " + action,
                            Toast.LENGTH_SHORT).show();
                }

            }
*/





 /*
    @Override
    protected void onResume() {
        super.onResume();

        *//**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         *//*
        setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        *//**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         *//*
        stopForegroundDispatch(this, nfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        *//**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         *//*
        handleIntent(intent);
    }

    *//**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     *//*
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Log.i("tagi", "löytyi");
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }


    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];
            Log.i("viesti", "täällä");
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
        *//*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         *//*

            byte[] payload = record.getPayload();

            String textEncoding;
            // Get the Text Encoding
            if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
            else textEncoding = "UTF-16";
            //String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                textViewInfo.setText("Read content: " + result);
            }
        }
    }*/
