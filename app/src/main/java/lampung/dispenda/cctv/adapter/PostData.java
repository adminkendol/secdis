package lampung.dispenda.cctv.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 10/8/2016.
 */
public class PostData extends AsyncTask<String, Void, HttpResponse> {
    Context context;
    List<NameValuePair> Paramsx;
    ProgressDialog pDialog;
    String action;
    public PostData(Context context, List<NameValuePair> Paramsx, String action) {
        this.context=context;
        this.Paramsx=Paramsx;
        this.action=action;

        //progress.show();
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*pDialog = new ProgressDialog(context);
        pDialog.setMessage("Mohon Tunggu..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);*/
        pDialog = ProgressDialog.show(context, null, null, true);
        pDialog.setContentView(R.layout.loading);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        pDialog.show();
    }
    @Override
    protected HttpResponse doInBackground(String... Params) {
        //return null;
        Basic config = new Basic();
        String url = null;
        if(action.equals("user")) {
            url = config.setSETUP_POST_USER();
        }
        if(action.equals("loc")) {
            url = config.setSETUP_POST_LOC();
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        HttpResponse response = null;
        try {
            // Add your data
            //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("myHttpData", ""));
            httppost.setEntity(new UrlEncodedFormEntity(Paramsx));

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);

            String temp = EntityUtils.toString(response.getEntity());

            Log.d("POST DATA", temp);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return response;
    }
    protected void onPostExecute(HttpResponse result) {
        super.onPostExecute(result);
        pDialog.dismiss();
    }
}
