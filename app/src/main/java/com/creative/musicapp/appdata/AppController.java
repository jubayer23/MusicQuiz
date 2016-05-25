package com.creative.musicapp.appdata;

import android.app.Application;

import com.creative.musicapp.util.PrefManager;

/**
 * Created by comsol on 10/26/2015.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;


    private float scale;

    private PrefManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        pref = new PrefManager(this);

        int density = getResources().getDisplayMetrics().densityDpi;
        this.scale = getResources().getDisplayMetrics().density;


        // startService(new Intent(this, TrackLocation.class));

    }

    public PrefManager getPrefManger() {
        if (pref == null) {
            pref = new PrefManager(this);
        }

        return pref;
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public int getPixelValue(int dps) {
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }


}