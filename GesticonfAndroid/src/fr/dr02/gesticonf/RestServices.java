package fr.dr02.gesticonf;

import android.content.Context;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by damien on 06/02/14.
 */
public class RestServices {
    private static RestServices ourInstance = new RestServices();

    public static RestServices getInstance() {
        return ourInstance;
    }

    private RestServices() {}
    private Context context;
    private String ip = context.getResources().getString(R.string.ip_server);
    private String urlGetConf = ip+context.getResources().getString(R.string.url_get_conf);
    private String urlGetPresByConf = ip+context.getResources().getString(R.string.url_get_pres_by_conf);
    private String urlAddDevice = ip+context.getResources().getString(R.string.url_add_device);

    public JSONObject currentConf = null;
    public JSONArray currentPresentations = null;

    public void setContext(Context context) {
        this.context = context;
    }

    public void findConference(int idConf){
        HttpClient httpclient= new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(urlGetConf+"/"+idConf);
        HttpResponse httpresponse= null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity=httpresponse.getEntity();

            if (httpentity!=null){
                StringBuilder stringBuilder = streamToStrBuilder(httpentity.getContent());
                this.currentConf = new JSONObject(stringBuilder.toString());
            }
        } catch (Exception e) {}

    }

    public void findPresentationsByConf(int idConf){
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlGetPresByConf+"/"+idConf);
        HttpResponse httpresponse= null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity = httpresponse.getEntity();

            if (httpentity!=null){
                StringBuilder stringBuilder = streamToStrBuilder(httpentity.getContent());
                this.currentPresentations = new JSONArray(stringBuilder.toString());
            }
        } catch (Exception e) {}
    }

    public void addDevice(String idDevice, String idRegistration){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlAddDevice);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("idDevice", idDevice);
            jsonObject.accumulate("idRegistration", idRegistration);
            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            httpclient.execute(httpPost);
        } catch (Exception e) {}
    }

    public StringBuilder streamToStrBuilder(InputStream is) throws IOException {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(is));
        StringBuilder strBuilder = new StringBuilder();
        String ligne;
        while ( (ligne=bufferedreader.readLine())!=null)
            strBuilder.append(ligne + "n");

        bufferedreader.close();
        return strBuilder;
    }


}
