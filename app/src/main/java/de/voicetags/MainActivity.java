package de.voicetags;

import android.speech.tts.TextToSpeech;
import android.util.Log;


public class MainActivity extends SpeakActivity {

    private int ttsStatus = Integer.MIN_VALUE;

    public void onResume() {
        super.onResume();

        if (ttsStatus == TextToSpeech.SUCCESS) {
            Log.i("info", "Main speak");
            speak();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsStatus = status;
            Log.i("info", "TTS initialized!");
            Log.i("info", "Main speak");
            speak();
        } else {
            Log.e("error", "Initialization Failed!");
        }
    }
}
