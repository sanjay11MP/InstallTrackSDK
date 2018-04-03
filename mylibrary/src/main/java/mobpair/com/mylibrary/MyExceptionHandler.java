package mobpair.com.mylibrary;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ${Mobpair} on 19/2/18.
 */

/**
 * when UncaughtException occur and crash app that this class help to reopen app
 */
public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Application activity;

    public MyExceptionHandler(Application a) {
        activity = a;
    }

    /**
     * Reopen app if app is crash
     *
     * @param thread
     * @param throwable thorough the particilar Error by crash app
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("MyExceptioHandler", "" + throwable);

        Intent intent = new Intent(activity, InstallReferrerReceiver.class);
        intent.putExtra("crash", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

       /* PendingIntent pendingIntent = PendingIntent.getActivity(activity., 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) ApplicationLifecycleHandler.getInstance().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 70000, pendingIntent);
        System.exit(2);*/
    }
}
