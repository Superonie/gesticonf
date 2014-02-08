package fr.dr02.gesticonf;

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

    private String ip = "http://192.168.1.11:8080";
    private String urlGetConf = ip+"/RS/conferenceEntityManagerRS";
    private String urlGetPresByConf = ip+"/RS/presentationEntityManagerRS";
    private String urlAddDevice = ip+"/RS/deviceEntityManagerRS/addDevice";

    public JSONObject currentConf = null;
    public JSONArray currentPresentations = null;

    public void findConference(int idConf){
        HttpClient httpclient= new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(urlGetConf+"/"+idConf);
        HttpResponse httpresponse= null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity=httpresponse.getEntity();

            if (httpentity!=null){
                InputStream inputstream=httpentity.getContent();
                BufferedReader bufferedreader=new BufferedReader(
                        new InputStreamReader(inputstream));
                StringBuilder strBuilder=new StringBuilder();
                String ligne=bufferedreader.readLine();
                while (ligne!=null){
                    strBuilder.append(ligne + "n");
                    ligne=bufferedreader.readLine();
                }
                bufferedreader.close();

                this.currentConf = new JSONObject(strBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void findPresentationsByConf(int idConf){
        HttpClient httpclient= new DefaultHttpClient();

        Log.i("TAG EUL", urlGetPresByConf+"/"+idConf);

        HttpGet httpGet = new HttpGet(urlGetPresByConf+"/"+idConf);
        HttpResponse httpresponse= null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity=httpresponse.getEntity();

            if (httpentity!=null){
                InputStream inputstream=httpentity.getContent();
                BufferedReader bufferedreader=new BufferedReader(
                        new InputStreamReader(inputstream));
                StringBuilder strBuilder=new StringBuilder();
                String ligne=bufferedreader.readLine();
                while (ligne!=null){
                    strBuilder.append(ligne + "n");
                    ligne=bufferedreader.readLine();
                }
                bufferedreader.close();

                this.currentPresentations = new JSONArray(strBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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



}
