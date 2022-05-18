package de.voicetags;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;


public class ReaderActivity extends SpeakActivity {

    private int ttsStatus = Integer.MIN_VALUE;

    public void onResume() {
        super.onResume();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                NdefMessage[] msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }

                TextView nfcContentView = (TextView) findViewById(R.id.nfcContent);
                nfcContentView.setText(new String(msgs[0].getRecords()[0].getPayload()).substring(3));

                if (ttsStatus == TextToSpeech.SUCCESS) {
                    Log.i("info", "NFC speak");
                    speak();
                }
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsStatus = status;
            Log.i("info", "TTS initialized!");
            Log.i("info", "NFC speak");
            speak();
        } else
            Log.e("error", "Initialization Failed!");
    }
}
