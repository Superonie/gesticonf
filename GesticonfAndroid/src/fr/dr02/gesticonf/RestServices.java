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

    private String ip, urlGetConf, urlGetPresByConf, urlAddDevice;

    public JSONObject currentConf = null;
    public JSONArray currentPresentations = null;

    public void init(String ip, String urlGetConf, String urlGetPresByConf, String urlAddDevice ) {
        this.ip = ip;
        this.urlGetConf = urlGetConf;
        this.urlGetPresByConf = urlGetPresByConf;
        this.urlAddDevice = urlAddDevice;
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
        } catch (Exception e) {e.printStackTrace();}

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
        } catch (Exception e) {e.printStackTrace();}
    }

    public void addDevice(String idDevice, String idRegistration, int refConference){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlAddDevice);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("idDevice", idDevice);
            jsonObject.accumulate("idRegistration", idRegistration);
            jsonObject.accumulate("refConference",refConference);
            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            httpclient.execute(httpPost);
        } catch (Exception e) {e.printStackTrace();}
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
