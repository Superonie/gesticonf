package fr.dr02.gesticonf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * Created by damien on 07/02/14.
 */
public class JSONArrayConferenceAdapter extends BaseAdapter {

    private ViewGroup group;
    private JSONArray items;
    private Context context;
    private String selectedItemID;
    private String currentDate;
    private int selectedItemPosition;

    public JSONArrayConferenceAdapter(Context ctx, JSONArray array) {
        super();
        this.currentDate = "";
        this.items = array;
        this.context = ctx;
        this.selectedItemPosition = -1;
    }

    public int getCount() {
        return items.length();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        group = parent;


        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.lv_item, null);
        }

        String sujet = null;
        String heureDebut = null;
        String heureFin = null;
        String date = null;
        String orateurs = null;
        String resume = null;
        String lieu = null;

        try {
            JSONObject jsonObject = items.getJSONObject(position);
            sujet = jsonObject.getString("sujet");
            heureDebut = jsonObject.getString("heureDeb");
            heureFin = jsonObject.getString("heureFin");
            date = jsonObject.getString("date");
            lieu = jsonObject.getString("lieu");
            resume = jsonObject.getString("resume");
            orateurs = jsonObject.getString("orateurs");
        } catch (JSONException e) { }



        TextView tvSujet = (TextView) view.findViewById(R.id.sujet_presentation);
        TextView tvHeureD = (TextView) view.findViewById(R.id.heure_debut);
        TextView tvHeureF = (TextView) view.findViewById(R.id.heure_fin);
        TextView tvDate = (TextView) view.findViewById(R.id.date);
        TextView tvOrateurs = (TextView) view.findViewById(R.id.orateurs);
        TextView tvLieu = (TextView) view.findViewById(R.id.lieu);

        tvSujet.setText(sujet);
        tvHeureD.setText(heureDebut);
        tvHeureF.setText(heureFin);

        // Si c'est une nouvelle date, on l'écrit et on crée un espacement visuel
        //if ( !date.equals(currentDate) ) {
            tvDate.setText(dateBDDtodateLisible(date));
            view.setPadding(0,40,0,0);
        //}

        currentDate = date;

        tvOrateurs.setText(orateurs);
        tvLieu.setText(lieu);

        DateListener dl = new DateListener();
        tvDate.setOnClickListener(dl);

        LieuListener ll = new LieuListener();
        tvLieu.setOnClickListener(ll);

        ResumeListener rl = new ResumeListener(sujet,resume);
        tvSujet.setOnClickListener(rl);


        return view;
    }


    // convert 2014-01-01 en Lundi 1 janv
    public String dateBDDtodateLisible(String date) {
        String dFormatee = "";

        try {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

            Date d = df1.parse(date);
            DateFormat df2 = new SimpleDateFormat("EEEE d MMM", Locale.FRENCH);
            dFormatee = df2.format(d);


        } catch (ParseException e) { e.printStackTrace(); }

        return dFormatee;
    }



}