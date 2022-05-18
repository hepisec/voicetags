package de.voicetags;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SpeakFragment extends Fragment implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        tts = new TextToSpeech(getActivity(), this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onDestroy() {
        super.onDestroy();

        if (tts != null) {
            tts.shutdown();
        }
    }

    protected void speak(String text) {
        // check settings first, maybe the app is muted and shouldn't speak
        if (prefs.getBoolean("mute_all", false) || (!prefs.getBoolean("speak_main", true) && this instanceof WelcomeFragment)) {
            return;
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
