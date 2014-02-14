package fr.dr02.gesticonf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gcm.GCMRegistrar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConferenceActivity extends Activity {

    TextView tvNom, tvTheme, tvDate;
    ListView lvPresentations;
    ImageButton returnButton;
    private int idConf = -1;
    private static ConferenceActivity ourInstance;

    public static ConferenceActivity getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //TODO Perfectionnable
        // Utilisation d'Internet dans le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        idConf = Math.max(idConf, findIdConf());

        Log.i("TAG EUL","IDCONF = "+ idConf);

        Uri data = getIntent().getData();
        if (data != null)
            if (data.getPathSegments().size() > 0)
                idConf = Integer.valueOf(data.getPathSegments().get(0));

        if ( idConf != -1 ) {
            initRS();
            load(idConf);
            registerGCM(idConf);
        } else {
            TextView tvInform = new TextView(getApplicationContext());
            tvInform.setText("Aucune conférence liée à l'application");
            tvInform.setTextSize(20);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout rl = new RelativeLayout(getApplicationContext());

            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            rl.addView(tvInform,params);
            setContentView(rl);
        }


        ourInstance = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        idConf = Math.max(idConf, findIdConf());

        Log.i("TAG EUL","IDCONF = "+ idConf);

        Uri data = getIntent().getData();
        if (data != null)
            if (data.getPathSegments().size() > 0)
                idConf = Integer.valueOf(data.getPathSegments().get(0));

        if ( idConf != -1 ) {
            initRS();
            load(idConf);
            registerGCM(idConf);
        } else {
            TextView tvInform = new TextView(getApplicationContext());
            tvInform.setText("Aucune conférence liée à l'application");
            tvInform.setTextSize(20);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout rl = new RelativeLayout(getApplicationContext());

            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            rl.addView(tvInform,params);
            setContentView(rl);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("idConf", idConf);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        idConf = savedInstanceState.getInt("idConf");
    }

    public int findIdConf() {
        Uri data = getIntent().getData();
        if (data != null)
            if (data.getPathSegments().size() > 0)
                return Integer.valueOf(data.getPathSegments().get(0));

        return -1;
    }

    public void initRS() {
        String ip = getResources().getString(R.string.ip_server);
        String urlGetConf = ip + getResources().getString(R.string.url_get_conf);
        String urlGetPresByConf = ip + getResources().getString(R.string.url_get_pres_by_conf);
        String urlAddDevice = ip + getResources().getString(R.string.url_add_device);

        RestServices.getInstance().init(ip, urlGetConf, urlGetPresByConf, urlAddDevice);
    }

    public void load(int idConf) {

        if (idConf != -1) {

            RestServices.getInstance().findConference(idConf);

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = (View) inflater.inflate(R.layout.conference_view, null);

            setContentView(view);
            try {
                tvNom = (TextView) findViewById(R.id.nom_conf);
                tvTheme = (TextView) findViewById(R.id.theme_conf);
                tvDate = (TextView) findViewById(R.id.filtre_conf);
                returnButton = (ImageButton) findViewById(R.id.return_button);
                disableReturnButton();

                returnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvDate.setText("Aucun filtre");
                        JSONArrayConferenceAdapter jsonArrayAdapter = new JSONArrayConferenceAdapter(ConferenceActivity.getInstance(), RestServices.getInstance().currentPresentations);
                        lvPresentations.setAdapter(jsonArrayAdapter);
                        if (view instanceof Button)
                            disableReturnButton();
                    }
                });


                JSONObject currentConf = RestServices.getInstance().currentConf;
                tvNom.setText(currentConf.getString("nomConference"));
                tvTheme.setText(currentConf.getString("theme"));
                tvDate.setText("Aucun filtre");


                // On initialise les presentations actuelles si besoin
                //if (RestServices.getInstance().currentPresentations == null)
                RestServices.getInstance().findPresentationsByConf(idConf);

                lvPresentations = (ListView) findViewById(R.id.listview_presentations);
                JSONArrayConferenceAdapter jsonArrayAdapter = new JSONArrayConferenceAdapter(this, RestServices.getInstance().currentPresentations);
                lvPresentations.setAdapter(jsonArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void registerGCM(int idConf) {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String registrationId = GCMRegistrar.getRegistrationId(this);
        GCMRegistrar.register(this, getResources().getString(R.string.id_project));

        RestServices.getInstance().addDevice(androidId, registrationId, idConf);
    }

    public void enableReturnButton() {
        returnButton.setEnabled(true);
        returnButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_revert_enabled));
    }

    public void disableReturnButton() {
        returnButton.setEnabled(false);
        returnButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_revert));
    }

    public void createResumePopUp(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        LinearLayout lila= new LinearLayout(this);
        lila.setOrientation(1); //1 is for vertical orientation

        final TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        final TextView tvMessage = new TextView(this);
        tvMessage.setText(message);
        lila.addView(tvTitle);
        lila.addView(tvMessage);

        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setView(lila)
                .setPositiveButton("Fermer",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();;
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}