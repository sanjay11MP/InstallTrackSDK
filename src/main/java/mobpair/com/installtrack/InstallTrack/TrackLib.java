package mobpair.com.installtrack.InstallTrack;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by ${Mobpair} on 21/3/18.
 */

public class TrackLib {
    private String TAG = TrackLib.class.getName();
    private String REFFERER_VALUE = "referrer";
    private static TrackLib instance = new TrackLib();
    private Util util;

    public static TrackLib getInstance() {
        return instance;
    }

    void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra(REFFERER_VALUE);

        if (referrer != null) {
            util = new Util(context);
            util.setRefferer(referrer);
            Log.d(TAG, "refferer : IF" + referrer);
        } else {
            Log.d(TAG, "refferer : Else");
        }
        Util util = new Util(context);
        util.SendDeviceId(new Util.CallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("RESULT@@", "" + result);
            }

            @Override
            public void onError(String error) {
                Log.d("RESULT@@", "Error" + error);
            }
        });
    }

    public void init(Application application) {
       /* if (util.getRefferer() != null) {
            String refferer_chk = util.getRefferer();
        }*/
/*
        if (refferer_chk != null) {

        } else {

        }*/

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            application.registerActivityLifecycleCallbacks(handler);
            application.registerComponentCallbacks(handler);
        }
    }

    public void updateFCMToken(String fcmToken) {

    }
}
