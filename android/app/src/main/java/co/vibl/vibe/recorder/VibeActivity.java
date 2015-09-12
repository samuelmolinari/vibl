package co.vibl.vibe.recorder;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import co.vibl.R;
import co.vibl.utils.State;
import co.vibl.utils.StatefulActivity;
import co.vibl.utils.Vibe;
import co.vibl.vibe.recorder.events.ResumeEvent;
import co.vibl.vibe.recorder.events.StartEvent;
import co.vibl.vibe.recorder.events.VibeShiftEvent;
import co.vibl.vibe.recorder.states.EndState;
import co.vibl.vibe.recorder.states.InitState;
import co.vibl.vibe.recorder.states.PausedState;
import co.vibl.vibe.recorder.states.RecordingState;
import co.vibl.vibe.recorder.states.StartState;
import co.vibl.widgets.VibeFootprintView;

public class VibeActivity extends AppCompatActivity implements StatefulActivity {
    private State currentState;

    private State initState;
    private State startState;
    private State recordingState;
    private State pauseState;
    private State endState;

    private Button startVibeButton;
    private Button resumeVibeButton;
    private View vibeSensorAreaView;
    private View trackPositionView;
    private VibeFootprintView vibeFootprintView;

    private Vibe vibe;

    AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);

        vibe = new Vibe();

        initState = new InitState();
        startState = new StartState();
        recordingState = new RecordingState();
        pauseState = new PausedState();
        endState = new EndState();

        startVibeButton = (Button) findViewById(R.id.startVibeButton);
        resumeVibeButton = (Button) findViewById(R.id.resumeVibeButton);
        vibeSensorAreaView = findViewById(R.id.vibeSensorArea);
        trackPositionView = findViewById(R.id.trackPosition);
        vibeFootprintView = (VibeFootprintView) findViewById(R.id.vibeFootprint);

        startVibeButton.setOnTouchListener(new StartEvent(this));
        resumeVibeButton.setOnTouchListener(new ResumeEvent(this));
        vibeSensorAreaView.setOnTouchListener(new VibeShiftEvent(this));

        vibeFootprintView.setVibe(vibe);

        initState.enter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean isStateless() {
        return currentState == null;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void setCurrentState(State state) {
        currentState = state;
    }

    public boolean isRecording() {
        return currentState == getRecordingState();
    }

    public Button getStartVibeButton() {
        return startVibeButton;
    }

    public Button getResumeVibeButton() {
        return resumeVibeButton;
    }

    public View getVibeSensorAreaView() {
        return vibeSensorAreaView;
    }

    public void startVibe() {
        startState.enter(this);
    }

    public void resumeVibe() {
        recordingState.enter(this);
    }

    public void pauseVibe(MotionEvent event) {
        ((PausedState) pauseState).enter(this, event);
    }

    public void endVibe() {
        endState.enter(this);
    }

    public State getRecordingState() {
        return recordingState;
    }

    public Vibe getVibe() {
        return vibe;
    }

    public View getTrackPositionView() {
        return trackPositionView;
    }

    public VibeFootprintView getVibeFootprintView() {
        return vibeFootprintView;
    }
}
