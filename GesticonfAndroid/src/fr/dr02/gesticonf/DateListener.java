package fr.dr02.gesticonf;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by damien on 12/02/14.
 */
public class DateListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        if ( view instanceof TextView ) {

            String dateSearched = ((TextView) view).getText().toString();
            ConferenceActivity.getInstance().tvDate.setText("Date = "+dateSearched);

            JSONArray allPres = RestServices.getInstance().currentPresentations;
            JSONArray filteredPres = new JSONArray();

            try {
                for (int i = 0; i < allPres.length(); i++) {
                    JSONObject presCourante = allPres.getJSONObject(i);
                    if ( presCourante.getString("date").equals(dateSearched) )
                        filteredPres.put(presCourante);
                }

                JSONArrayConferenceAdapter jsonArrayAdapter = new JSONArrayConferenceAdapter(ConferenceActivity.getInstance(), filteredPres);
                ConferenceActivity.getInstance().lvPresentations.setAdapter(jsonArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ConferenceActivity.getInstance().enableReturnButton();

        }
    }
}
