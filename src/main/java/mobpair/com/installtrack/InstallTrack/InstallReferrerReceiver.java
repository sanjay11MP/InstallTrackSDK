package mobpair.com.installtrack.InstallTrack;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mobpair.com.installtrack.InstallTrack.newabc;

/**
 * Created by ${Mobpair} on 6/3/18.
 */

public class InstallReferrerReceiver extends BroadcastReceiver {
    String TAG = "InstallReferrerReceiver";

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
      /*  Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
        List<ResolveInfo> receivers = context.getPackageManager().queryBroadcastReceivers(new Intent("com.android.vending.INSTALL_REFERRER"), 0);
        Log.d("ABC@@", "Hiiii" + receivers);
        for (ResolveInfo resolveInfo : receivers) {
            String action = intent.getAction();
            Log.d("ABC@@", "" + action);

            if (resolveInfo.activityInfo.packageName.equalsIgnoreCase(context.getPackageName())
                    && "com.android.vending.INSTALL_REFERRER".equalsIgnoreCase(action)
                    && !this.getClass().getName().equals(resolveInfo.activityInfo.name));
            {
                try {
                    BroadcastReceiver broadcastReceiver = (BroadcastReceiver) Class.forName(resolveInfo.activityInfo.name).newInstance();
                    broadcastReceiver.onReceive(context, intent);
                } catch (Throwable e) {
                    Log.d("ABC@@", "" + e.toString());
                }
            }
        }
        newabc.getInstance().onReceive(context, intent);*/

        Log.d(TAG, "refferer");
        String referrer = intent.getStringExtra("referrer");
        Log.d(TAG, "refferer" + referrer);

        ActivityInfo ai = null;
        try {
            ai = context.getPackageManager().getReceiverInfo(new ComponentName(context, "my.package.ReferrerCatcher"), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //extract meda-data
        assert ai != null;
        Bundle bundle = ai.metaData;
        Set<String> keys = bundle.keySet();

        //iterate through all metadata tags
        for (String k : keys) {
            String v = bundle.getString(k);
            try {
                ((BroadcastReceiver) Class.forName(v).newInstance()).onReceive(context, intent); //send intent by dynamically creating instance of receiver
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Log.i("PASS REFERRER TO...", v);
        }
/*
        InstallTrackClass util = new InstallTrackClass(context);
        util.SendDeviceId(new InstallTrackClass.CallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("RESULT@@", "" + result);
            }

            @Override
            public void onError(String error) {
                Log.d("RESULT@@", "Error" + error);
            }
        });*/
    }
}