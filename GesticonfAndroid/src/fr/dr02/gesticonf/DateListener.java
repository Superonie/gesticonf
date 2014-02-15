package fr.dr02.gesticonf;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by damien on 12/02/14.
 */
public class DateListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        if ( view instanceof TextView ) {

            String dateSearched = ((TextView) view).getText().toString();
            String dateSearchedFormatee = dateLisibletodateBDD(dateSearched);
            ConferenceActivity.getInstance().tvDate.setText("Date = "+dateSearched);

            JSONArray allPres = RestServices.getInstance().currentPresentations;
            JSONArray filteredPres = new JSONArray();

            try {
                for (int i = 0; i < allPres.length(); i++) {
                    JSONObject presCourante = allPres.getJSONObject(i);
                    if ( compareDate( presCourante.getString("date"), dateSearchedFormatee) )
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


    // convert Lundi 1 janv en 2014-01-01
    public String dateLisibletodateBDD(String date) {
        String dFormatee = "";

        try {
            SimpleDateFormat df1 = new SimpleDateFormat("EEEE d MMM", Locale.FRENCH);

            Date d = df1.parse(date);
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            dFormatee = df2.format(d);
        } catch (ParseException e) { e.printStackTrace(); }

        return dFormatee;
    }

    public boolean compareDate(String d1, String d2) {
        String m1 = d1.split("-")[1];
        String j1 = d1.split("-")[2];
        String m2 = d2.split("-")[1];
        String j2 = d2.split("-")[2];

        return ( m1.equals(m2) && j1.equals(j2) );
    }

}
