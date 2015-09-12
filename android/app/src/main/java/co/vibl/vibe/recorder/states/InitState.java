package co.vibl.vibe.recorder.states;

import android.view.View;
import co.vibl.utils.StatefulActivity;

/**
 * Created by Samuel on 11/09/15.
 */
public class InitState extends AbstractVibeState {
    @Override
    public void enter(StatefulActivity activity) {
        super.enter(activity);
        showRelevantViews();
    }

    private void showRelevantViews() {
        hideResumeButton();
        showStartButton();
    }

    private void hideResumeButton() {
        getVibeActivity().getResumeVibeButton().setVisibility(View.INVISIBLE);
    }

    private void showStartButton() {
        getVibeActivity().getStartVibeButton().setVisibility(View.VISIBLE);
    }
}
