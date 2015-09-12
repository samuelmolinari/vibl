package co.vibl.vibe.recorder.events;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import java.util.TimerTask;

import co.vibl.vibe.recorder.VibeActivity;

/**
 * Created by Samuel on 12/09/15.
 */
public class DrawFootprintEvent extends AbstractVibeEvent implements Runnable {
    public DrawFootprintEvent(VibeActivity activity) {
        super(activity);
    }

    @Override
    public void run() {
        if(getVibeActivity().getVibe().percentage() >= 1) {
            getVibeActivity().endVibe();
        } else {
            moveCursor();
            drawFootprint();
        }
    }

    private void moveCursor() {
        View view = getVibeActivity().getTrackPositionView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        float percentage = getVibeActivity().getVibe().percentage();

        params.setMargins((int) Math.ceil(((View) view.getParent()).getMeasuredWidth() * percentage), 0, 0, 0);
        view.setLayoutParams(params);
    }

    private void drawFootprint() {
        getVibeActivity().getVibeFootprintView().invalidate();
    }
}
