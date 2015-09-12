package co.vibl.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import co.vibl.utils.Vibe;

/**
 * Created by Samuel on 12/09/15.
 */
public class VibeFootprintView extends View {

    private Vibe vibe;
    private Bitmap bitmap;
    private long lastWindowDrawn;
    private Paint paint;
    private byte lastKnownValue;

    public VibeFootprintView(Context context) {
        super(context);
        init();
    }

    public VibeFootprintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VibeFootprintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        lastWindowDrawn = 0;
        paint = new Paint();
        paint.setAntiAlias(true);
        bitmap = null;
        lastKnownValue = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(vibe != null && vibe.isReady()) {
            if(bitmap == null) {
                bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                        Bitmap.Config.ARGB_8888);
            }

            Canvas cacheCanvas = new Canvas(bitmap);

            paint.setColor(Color.BLUE);

            int vibeSize = vibe.getFootprint().length;
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            int jump = 1;
            int widthPart = Math.max(Math.round((width / (float) vibeSize) * jump), 1);
            long estimatedElapsedTime = vibe.getEstimatedPlaybackPosition();

            for(int i=(int)lastWindowDrawn; i < estimatedElapsedTime; i = i + jump) {
                byte value = vibe.getFootprint()[i];
                if(value > 0) {
                    lastKnownValue = value;
                }

                value = lastKnownValue;
                int left = Math.round((i * width) / (float) vibeSize);
                int top = Math.round(height - (height * (value / 100f)));
                int right = left + widthPart;
                int bottom = height;
                cacheCanvas.drawRect(left, top, right, bottom, paint);
            }

            lastWindowDrawn = estimatedElapsedTime;
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
        }
    }

    public void setVibe(Vibe vibe) {
        this.vibe = vibe;
    }
}
