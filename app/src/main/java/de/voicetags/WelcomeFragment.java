package de.voicetags;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WelcomeFragment extends SpeakFragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_welcome, container, false);
        return rootView;
    }

    private int ttsStatus = Integer.MIN_VALUE;

    public void onResume() {
        super.onResume();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getActivity().getIntent().getAction())) {
            Parcelable[] rawMsgs = getActivity().getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                NdefMessage[] msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }

                TextView nfcContentView = (TextView) getActivity().findViewById(R.id.fragWelcomeContent);
                nfcContentView.setText(new String(msgs[0].getRecords()[0].getPayload()).substring(3));

                if (ttsStatus == TextToSpeech.SUCCESS) {
                    Log.i("info", "NFC speak");
                    speak(nfcContentView.getText().toString());
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
//            speak();
        } else
            Log.e("error", "Initialization Failed!");
    }
}
