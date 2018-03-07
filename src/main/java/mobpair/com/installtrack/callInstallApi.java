package mobpair.com.installtrack;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by ${Mobpair} on 6/3/18.
 */

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class callInstallApi extends AsyncTask<String, String, String> {
    private String deviceId;
    private String url;

    public callInstallApi(String deviceId) {
        this.deviceId = deviceId;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "preExecute");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBack");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("click", "Y");
        hashMap.put("nc", "1");
        hashMap.put("user_id", "41");
        hashMap.put("offer_id", "942");
        hashMap.put("format", "JSON");
        hashMap.put("track1", deviceId);
        Log.d(TAG, ":PARA : " + hashMap);
        return Util.getResponseofPost("https://tr.adsx.bid/affapi.php?", hashMap);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute");
        Log.d(TAG, "Data : " + s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String clickId = jsonObject.getString("clickid");
            Log.d(TAG, "ClickId : " + clickId);
        } catch (Exception e) {
            Log.d(TAG, "ErrorPost : " + e.toString());
        }
    }
}
