package co.vibl.vibe.recorder.states;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import co.vibl.utils.Music;
import co.vibl.utils.MusicController;
import co.vibl.utils.StatefulActivity;

/**
 * Created by Samuel on 11/09/15.
 */
public class PausedState extends AbstractVibeState {
    public void enter(StatefulActivity activity, MotionEvent motionEvent) {
        super.enter(activity);
        pauseMusic();
        showResumeButton(motionEvent.getX(), motionEvent.getY());
    }

    private void showResumeButton(float x, float y) {
        positionResumeButton((int) x, (int) y);
        getVibeActivity().getResumeVibeButton().setVisibility(View.VISIBLE);
    }

    private void positionResumeButton(int x, int y) {
        Button btn = resumeButton();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn.getLayoutParams();

        int offsetY = (btn.getMeasuredHeight() + btn.getPaddingTop() + btn.getPaddingBottom()) / 2;

        params.setMargins(0, y - offsetY, 0, 0);
        resumeButton().setLayoutParams(params);
    }

    private void pauseMusic() {
        MusicController.getInstance().pause(new MusicController.Callback() {
            @Override
            public void onConsume(Music music) {
                getVibeActivity().getVibe().setPlaybackPosition(music.getPlaybackPosition());
            }

            @Override
            public boolean isConsumable(Music music) {
                return true;
            }
        });
    }

    private Button resumeButton() {
        return getVibeActivity().getResumeVibeButton();
    }
}
