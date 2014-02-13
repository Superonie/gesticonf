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

/**
 * Created by damien on 07/02/14.
 */
public class JSONArrayConferenceAdapter extends BaseAdapter {

    private ViewGroup group;
    private JSONArray items;
    private Context context;
    private String selectedItemID;
    private int selectedItemPosition;

    public JSONArrayConferenceAdapter(Context ctx, JSONArray array) {
        super();
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
        String lieu = null;

        try {
            JSONObject jsonObject = items.getJSONObject(position);
            sujet = jsonObject.getString("sujet");
            heureDebut = jsonObject.getString("heureDeb");
            heureFin = jsonObject.getString("heureFin");
            date = jsonObject.getString("date");
            lieu = jsonObject.getString("lieu");
            orateurs = jsonObject.getString("orateurs");

            Log.i("TAG EUL","OBJ "+jsonObject.toString());


        } catch (JSONException e) { }

        TextView tvSujet = (TextView) view.findViewById(R.id.sujet_presentation);
        TextView tvHeureD = (TextView) view.findViewById(R.id.heure_debut);
        TextView tvHeureF = (TextView) view.findViewById(R.id.heure_fin);
        TextView tvDate = (TextView) view.findViewById(R.id.date);
        TextView tvOrateurs = (TextView) view.findViewById(R.id.orateurs);
        TextView tvLieu = (TextView) view.findViewById(R.id.lieu);

        Log.i("TAG EUL", sujet + " " + heureDebut + " " + heureFin + " " + date + " " + orateurs + " " + lieu);

        tvSujet.setText(sujet);
        tvHeureD.setText(heureDebut);
        tvHeureF.setText(heureFin);
        tvDate.setText(date);
        tvOrateurs.setText(orateurs);
        tvLieu.setText(lieu);

        DateListener dl = new DateListener();
        tvDate.setOnClickListener(dl);

        LieuListener ll = new LieuListener();
        tvLieu.setOnClickListener(ll);

        return view;
    }
}