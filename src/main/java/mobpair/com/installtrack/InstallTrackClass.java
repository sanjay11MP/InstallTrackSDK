package mobpair.com.installtrack;

/**
 * Created by ${Mobpair} on 6/3/18.
 */


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * This Class Will Call Only When AppInstall Receiver Is Called
 */
public class InstallTrackClass extends Activity {

    String TAG = "InstallTrackClass";

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        new callInstallApi(Util.DeviceId(this)).execute();
    }
}
