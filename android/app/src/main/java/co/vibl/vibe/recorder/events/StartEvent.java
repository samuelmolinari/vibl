package co.vibl.vibe.recorder.events;

import android.view.MotionEvent;
import android.view.View;

import co.vibl.vibe.recorder.VibeActivity;

/**
 * Created by Samuel on 12/09/15.
 */
public class StartEvent extends AbstractVibeEvent implements View.OnTouchListener {
    public StartEvent(VibeActivity activity) {
        super(activity);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                getVibeActivity().startVibe();
                break;
        }
        return false;
    }
}
