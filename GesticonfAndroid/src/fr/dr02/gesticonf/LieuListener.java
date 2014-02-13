package fr.dr02.gesticonf;

import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by damien on 12/02/14.
 */
public class LieuListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        if ( view instanceof TextView) {

            String lieuSearched = ((TextView) view).getText().toString();
            ConferenceActivity.getInstance().tvDate.setText("Lieu ="+lieuSearched);

            JSONArray allPres = RestServices.getInstance().currentPresentations;
            JSONArray filteredPres = new JSONArray();

            try {
                for (int i = 0; i < allPres.length(); i++) {
                    JSONObject presCourante = allPres.getJSONObject(i);
                    if ( presCourante.getString("lieu").equals(lieuSearched) )
                        filteredPres.put(presCourante);
                }

                JSONArrayConferenceAdapter jsonArrayAdapter = new JSONArrayConferenceAdapter(ConferenceActivity.getInstance(), filteredPres);
                ConferenceActivity.getInstance().lvPresentations.setAdapter(jsonArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ConferenceActivity.getInstance().returnButton.setEnabled(true);

            ConferenceActivity.getInstance().enableReturnButton();

        }
    }
}
