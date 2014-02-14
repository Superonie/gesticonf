package fr.dr02.gesticonf;

import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by damien on 14/02/14.
 */
public class ResumeListener implements View.OnClickListener {

    private String title, message;

    public ResumeListener(String t, String m) {
        this.title = t;
        this.message = m;
    }

    @Override
    public void onClick(View view) {
        if ( view instanceof TextView)
               ConferenceActivity.getInstance().createResumePopUp(title,message);

    }
}
