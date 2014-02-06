package fr.dr02.gesticonf.android;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
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
    private String urlGetConf = "/RS/conferenceEntityManagerRS";


    public JSONObject findConference(int idConf){
        boolean reponse=false;

        HttpClient httpclient= new DefaultHttpClient();

        HttpGet httpGet=
                new HttpGet(ip+urlGetConf+"/"+idConf);
        HttpResponse httpresponse= null;
        try {
            httpresponse = httpclient.execute(httpGet);

            HttpEntity httpentity=httpresponse.getEntity();


            if (httpentity!=null){
                InputStream inputstream=httpentity.getContent();
                BufferedReader bufferedreader=new BufferedReader(
                        new InputStreamReader(inputstream));
                StringBuilder strinbulder=new StringBuilder();
                String ligne=bufferedreader.readLine();
                while (ligne!=null){
                    strinbulder.append(ligne+"n");
                    ligne=bufferedreader.readLine();
                }
                bufferedreader.close();

                Log.i("TAG EUL", strinbulder.toString());

                JSONObject jsonConf = new JSONObject(strinbulder.toString());



                return jsonConf;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
