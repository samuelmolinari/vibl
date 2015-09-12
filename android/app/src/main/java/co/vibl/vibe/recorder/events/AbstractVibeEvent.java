package co.vibl.vibe.recorder.events;

import co.vibl.vibe.recorder.VibeActivity;

/**
 * Created by Samuel on 12/09/15.
 */
public abstract class AbstractVibeEvent {
    private VibeActivity vibeActivity;

    public AbstractVibeEvent(VibeActivity activity) {
        vibeActivity = activity;
    }

    public VibeActivity getVibeActivity() {
        return vibeActivity;
    }
}
