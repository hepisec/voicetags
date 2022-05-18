package de.voicetags;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Hendrik on 10.04.2015.
 */
public abstract class SpeakActivity extends AbstractVoiceTagsActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        tts = new TextToSpeech(this, this);
    }

    protected void onDestroy() {
        super.onDestroy();

        if (tts != null) {
            tts.shutdown();
        }
    }

    protected void speak() {
        TextView nfcContentView = (TextView) findViewById(R.id.nfcContent);
        speak(nfcContentView.getText().toString());
    }

    protected void speak(String text) {
        // check settings first, maybe the app is muted and shouldn't speak
        if (prefs.getBoolean("mute_all", false) || (!prefs.getBoolean("speak_main", true) && this instanceof MainActivity)) {
            return;
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
