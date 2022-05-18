package de.voicetags;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Hendrik Pilz on 13.04.2015.
 */
public class CreateActivity extends SpeakActivity {

    private int ttsStatus = Integer.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    public void onResume() {
        super.onResume();

        if (ttsStatus == TextToSpeech.SUCCESS) {
            Log.i("info", "Create speak");
            TextView createTextView = (TextView) findViewById(R.id.createText);
            speak(createTextView.getText().toString());
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsStatus = status;
            Log.i("info", "TTS initialized!");
            Log.i("info", "Create speak");
            TextView createTextView = (TextView) findViewById(R.id.createText);
            speak(createTextView.getText().toString());
        } else {
            Log.e("error", "Initialization Failed!");
        }
    }
}
