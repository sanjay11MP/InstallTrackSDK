package mobpair.com.newlibprj;

import android.app.Application;
import android.util.Log;

import mobpair.com.mylibrary.InternetConnectionClass;
import mobpair.com.mylibrary.TrackLib;

import static android.content.ContentValues.TAG;

/**
 * Created by ${Mobpair} on 29/3/18.
 */
public class myapplicationclass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TrackLib.getInstance().init(this);

        if (InternetConnectionClass.getInstance(this).isOnline()) {
            Log.d(TAG, ":IF");
        } else {
            Log.d(TAG, ":ELSE");
        }
    }
}