package fr.dr02.gesticonf;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gcm.GCMRegistrar;
import fr.dr02.gesticonf.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ConferenceActivity extends Activity {

    TextView tvNom, tvTheme, tvDebut, tvFin;
    ListView lvPresentations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Perfectionnable
        // Utilisation d'Internet dans le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        register();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public void register() {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String registrationId = GCMRegistrar.getRegistrationId(this);
        GCMRegistrar.register(this,getResources().getString(R.string.id_project));
        RestServices.getInstance().addDevice(androidId,registrationId);
    }

    public void init() {
        Uri data = getIntent().getData();
        if (data != null) {
            List<String> params = data.getPathSegments();
            if (params.size() > 0) { 
                int idConf = Integer.valueOf(params.get(0));
                RestServices.getInstance().findConference(idConf);

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = (View) inflater.inflate(R.layout.conference_view, null);
                setContentView(view);
                try {
                    tvNom = (TextView) findViewById(R.id.nom_conf);
                    tvTheme = (TextView) findViewById(R.id.theme_conf);
                    tvDebut = (TextView) findViewById(R.id.date_debut_conf);
                    tvFin = (TextView) findViewById(R.id.date_fin_conf);

                    RestServices.getInstance().findPresentationsByConf(idConf);
                    JSONObject currentConf = RestServices.getInstance().currentConf;
                    tvNom.setText(currentConf.getString("nomConference"));
                    tvTheme.setText(currentConf.getString("theme"));
                    tvDebut.setText(currentConf.getString("dateDebut"));
                    tvFin.setText(currentConf.getString("dateFin"));

                    JSONArray currentPresentations = RestServices.getInstance().currentPresentations;
                    lvPresentations = (ListView) findViewById(R.id.listview_presentations);
                    JSONArrayConferenceAdapter jsonArrayAdapter = new JSONArrayConferenceAdapter(this, currentPresentations);
                    lvPresentations.setAdapter(jsonArrayAdapter);

                } catch (JSONException e) {}
            }
        }

    }
}