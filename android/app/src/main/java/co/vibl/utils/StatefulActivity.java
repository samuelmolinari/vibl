package co.vibl.utils;

/**
 * Created by Samuel on 11/09/15.
 */
public interface StatefulActivity {
    public boolean isStateless();
    public State getCurrentState();
    public void setCurrentState(State state);
}
