package co.vibl.utils;

import android.util.Log;

/**
 * Created by Samuel on 11/09/15.
 */
public class State {
    public void enter(StatefulActivity activity) {
        if (activity.getCurrentState() != null) {
            Log.v("About to exit", activity.getCurrentState().getClass().getName());
            activity.getCurrentState().exit();
        }
        activity.setCurrentState(this);
    }

    public void exit() {
    }
}
