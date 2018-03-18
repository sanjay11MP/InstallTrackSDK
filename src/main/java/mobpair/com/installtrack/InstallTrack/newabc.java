package mobpair.com.installtrack.InstallTrack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ${Mobpair} on 18/3/18.
 */

public class newabc {
    private static newabc instance = new newabc();

    public static newabc getInstance() {
        return instance;
    }

    public void onReceive(Context context, Intent intent) {
        Log.d("ABC@@", "newabc");
    }
}
