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

    private RestServices() {
    }

    private String ip, urlGetConf, urlGetPresByConf, urlAddDevice, urlGlobalIdAvailable, urlFindDevice, urlFindConference;
    private int idGlobal = -1;

    public JSONObject currentConf = null;
    public JSONArray currentPresentations = null;
    public JSONArray allConfs = null;

    public void init(String ip, String urlGetConf, String urlGetPresByConf, String urlAddDevice, String urlGlobalId, String urlFindDevice, String urlFindConference) {
        this.ip = ip;
        this.urlGetConf = urlGetConf;
        this.urlGetPresByConf = urlGetPresByConf;
        this.urlAddDevice = urlAddDevice;
        this.urlGlobalIdAvailable = urlGlobalId;
        this.urlFindDevice = urlFindDevice;
        this.urlFindConference = urlFindConference;
    }

    public void findConference(int idConf) {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(urlGetConf + "/" + idConf);
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity = httpresponse.getEntity();

            if (httpentity != null) {
                StringBuilder stringBuilder = streamToStrBuilder(httpentity.getContent());
                this.currentConf = new JSONObject(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void findPresentationsByConf(int idConf) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlGetPresByConf + "/" + idConf);
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity = httpresponse.getEntity();

            if (httpentity != null) {
                StringBuilder stringBuilder = streamToStrBuilder(httpentity.getContent());
                this.currentPresentations = new JSONArray(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDevice(String idDevice, String idRegistration, int refConference) {

        if (!findDevice(refConference + "", idDevice)) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlAddDevice);

                JSONObject jsonObject = new JSONObject();

                int idGlobal = findIdAvailable();

                if (idGlobal != -1) {
                    jsonObject.accumulate("idGlobal", idGlobal);
                    jsonObject.accumulate("idDevice", idDevice);
                    jsonObject.accumulate("idRegistration", idRegistration);
                    jsonObject.accumulate("refConference", refConference);
                    StringEntity se = new StringEntity(jsonObject.toString());
                    Log.i("TAG RANMAIR", jsonObject.toString());

                    httpPost.setEntity(se);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");

                    httpclient.execute(httpPost);
                }
            } catch (Exception e) {
                Log.i("TAG RANMAIR", "ERRROR");
                e.printStackTrace();
            }
        }
    }

    public boolean findDevice(String idConf, String idDevice) {
        boolean reponse = false;
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(urlFindDevice + "/" + idConf + "/" + idDevice);
        Log.i("TAG EUL","FIND "+urlFindDevice + "/" + idConf + "/" + idDevice);
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpGet);

            HttpEntity httpentity = httpresponse.getEntity();

            if (httpentity != null) {
                InputStream inputstream = httpentity.getContent();
                BufferedReader bufferedreader = new BufferedReader(
                        new InputStreamReader(inputstream));
                StringBuilder strinbulder = new StringBuilder();
                String ligne = bufferedreader.readLine();
                while (ligne != null) {
                    strinbulder.append(ligne + "n");
                    ligne = bufferedreader.readLine();
                }
                bufferedreader.close();


                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int findIdAvailable() {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urlGlobalIdAvailable);
            HttpResponse httpresponse = null;
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity = httpresponse.getEntity();

            if (httpentity != null) {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpentity.getContent()));
                StringBuilder strBuilder = new StringBuilder();
                String ligne;
                while ((ligne = bufferedreader.readLine()) != null)
                    strBuilder.append(ligne);

                bufferedreader.close();
                Log.i("TAG RANMAIR", strBuilder.toString());


                return Integer.valueOf(strBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void findConfByIdDevice(String idDevice) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlFindConference + "/" + idDevice);
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpGet);
            HttpEntity httpentity = httpresponse.getEntity();

            if (httpentity != null) {
                StringBuilder stringBuilder = streamToStrBuilder(httpentity.getContent());
                this.allConfs = new JSONArray(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuilder streamToStrBuilder(InputStream is) throws IOException {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(is));
        StringBuilder strBuilder = new StringBuilder();
        String ligne;
        while ((ligne = bufferedreader.readLine()) != null)
            strBuilder.append(ligne + "n");

        bufferedreader.close();
        return strBuilder;
    }


}
