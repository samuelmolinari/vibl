package co.vibl.vibe.recorder.events;

import android.view.MotionEvent;
import android.view.View;

import co.vibl.vibe.recorder.VibeActivity;

/**
 * Created by Samuel on 12/09/15.
 */
public class ResumeEvent extends AbstractVibeEvent implements View.OnTouchListener {
    public ResumeEvent(VibeActivity activity) {
        super(activity);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                getVibeActivity().resumeVibe();
                break;
        }
        return false;
    }
}
