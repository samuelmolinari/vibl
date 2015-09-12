package co.vibl;

import android.app.Application;

/**
 * Created by Samuel on 12/09/15.
 */
public class ViblApplication extends Application {

    private static ViblApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static ViblApplication getInstance() {
        return instance;
    }
}
