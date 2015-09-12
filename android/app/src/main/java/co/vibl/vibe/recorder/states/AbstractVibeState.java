package co.vibl.vibe.recorder.states;

import android.util.Log;

import co.vibl.utils.State;
import co.vibl.utils.StatefulActivity;
import co.vibl.vibe.recorder.VibeActivity;

/**
 * Created by Samuel on 11/09/15.
 */
public abstract class AbstractVibeState extends State {
    private VibeActivity vibeActivity;

    public void enter(StatefulActivity activity) {
        super.enter(activity);
        vibeActivity = (VibeActivity) activity;
        Log.v("Enter State", this.getClass().getName());
    }

    public void exit() {
        Log.v("Exit State", this.getClass().getName());
    }

    public VibeActivity getVibeActivity() {
        return vibeActivity;
    }
}
