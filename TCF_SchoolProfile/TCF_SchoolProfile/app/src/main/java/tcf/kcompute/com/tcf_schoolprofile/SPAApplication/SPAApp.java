package tcf.kcompute.com.tcf_schoolprofile.SPAApplication;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class SPAApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
