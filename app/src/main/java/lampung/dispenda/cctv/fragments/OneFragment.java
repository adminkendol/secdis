package lampung.dispenda.cctv.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.JSONParser;
import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.Show;
import lampung.dispenda.cctv.adapter.ImageAdapter;
import lampung.dispenda.cctv.config.Basic;


public class OneFragment extends Fragment {
    GridView gridView;
    String gtId;
    String locId;
    public OneFragment(String group_type_id, String loc_id) {
        // Required empty public constructor
        this.gtId=group_type_id;
        this.locId=loc_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one,container,false);
        String cari = "";
        bindGridView(cari);
        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //builder.setTitle("Cari Lokasi");
                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.form_search, (ViewGroup) getView(), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String m_Text = input.getText().toString();
                        bindGridView(m_Text);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
        return view;
    }
    public void bindGridView(String cari){
        new ReadDataTask(getActivity(), gridView, cari).execute("");
    }

    class ReadDataTask extends AsyncTask<String, Void, String> {
        GridView mGridView;
        Activity mContex;
        String cari;
        ProgressDialog pDialog;

        public ReadDataTask(Activity activity, GridView gridView, String cari) {
            this.mGridView=gridView;
            this.mContex=activity;
            this.cari=cari;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.mContex);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = getData(cari);
            return returnResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result.equalsIgnoreCase("Exception Caught")) {
                Log.d("RESULT API", "data:" + String.valueOf(result) + ":END");
                Toast.makeText(this.mContex, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }else if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(this.mContex, "Data empty", Toast.LENGTH_LONG).show();
            } else {
                JSONObject data = null;
                try {
                    data = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Object data JSON", String.valueOf(result));
                try {
                    JSONArray cams;
                    cams = data.getJSONArray("data");
                    gridView = (GridView) mContex.findViewById(R.id.listOnline);
                    gridView.setAdapter(null);

                    String[] camId = new String[cams.length()];
                    String[] camName = new String[cams.length()];
                    String[] camIp = new String[cams.length()];
                    String[] camLive = new String[cams.length()];
                    String[] camRec = new String[cams.length()];
                    String[] camStatus = new String[cams.length()];
                    String[] camLoc = new String[cams.length()];
                    String[] camImg = new String[cams.length()];
                    Log.d("Array data cams", String.valueOf(cams));
                    for (int i = 0; i < cams.length(); i++) {
                        JSONObject c = cams.getJSONObject(i);
                        camId[i] = c.getString("camera_id");
                        camName[i] = c.getString("camera_name");
                        camIp[i] = c.getString("ip");
                        camLive[i] = c.getString("live");
                        camRec[i] = c.getString("rec");
                        camStatus[i] = c.getString("status");
                        camLoc[i] = c.getString("location_name");
                        camImg[i] = c.getString("img");
                    }
                    gridView.setAdapter(new ImageAdapter(this.mContex, camId, camName, camIp, camStatus, camLoc, camImg,camLive,camRec));
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            Intent intentB = new Intent(mContex, Show.class);
                            intentB.putExtra("ip", ((TextView) v.findViewById(R.id.ipCam)).getText());
                            intentB.putExtra("live", ((TextView) v.findViewById(R.id.liveCam)).getText());
                            intentB.putExtra("rec", ((TextView) v.findViewById(R.id.recCam)).getText());
                            mContex.startActivity(intentB);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String getData(String cari) {
            Basic config = new Basic();
            String url = config.getCAMONLINE();
            JSONParser jParser = new JSONParser();

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("un", "0"));
            parameter.add(new BasicNameValuePair("pass", "0"));
            parameter.add(new BasicNameValuePair("cari", cari));
            if(gtId.equals("3")){
                parameter.add(new BasicNameValuePair("loc_id", locId));
            }
            Log.d("URL ONLINE", url);
            JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
            if(String.valueOf(json).equals("null")){
                return "Exception Caught";
            }else {
                Log.d("RESULT DATA", String.valueOf(json));
                return String.valueOf(json);
            }
        }
    }
}
