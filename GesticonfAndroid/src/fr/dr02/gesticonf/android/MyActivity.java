package fr.dr02.gesticonf.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import org.json.JSONObject;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        //TODO Perfectionnable
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        JSONObject conf = RestServices.getInstance().findConference(2);
        if ( conf != null ) {
            Log.i("TAG EUL", conf.toString());
        }
        else
            Log.i("TAG EUL","SAI NUL");

    }
}
