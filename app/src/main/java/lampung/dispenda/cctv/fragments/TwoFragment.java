package lampung.dispenda.cctv.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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
import lampung.dispenda.cctv.Session;
import lampung.dispenda.cctv.adapter.RecyclerViewAdapter;
import lampung.dispenda.cctv.config.Basic;


public class TwoFragment extends Fragment {
    ListView listView;
    String gtId;
    String locId;
    public TwoFragment(String group_type_id, String loc_id) {
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
        View view = inflater.inflate(R.layout.fragment_two,container,false);
        String cari = "";
        bindListView(cari);
        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.fabB);
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
                        bindListView(m_Text);
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
    public void bindListView(String cari){
        new ReadDataTask(getActivity(), listView,cari).execute("");
    }

    class ReadDataTask extends AsyncTask<String, Void, String> {
        ListView mListView;
        Activity mContex;
        ProgressDialog pDialog;
        String cari;
        public ReadDataTask(Activity activity, ListView listView,String cari) {
            this.mListView=listView;
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
            String returnResult = null;
            try {
                returnResult = getData(cari);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                Log.d("Array data JSON REC", String.valueOf(result));
                try {
                    JSONArray cams;
                    cams = data.getJSONArray("data");
                    String[] camId = new String[cams.length()];
                    String[] camName = new String[cams.length()];
                    String[] camIp = new String[cams.length()];
                    String[] camLive = new String[cams.length()];
                    String[] camRec = new String[cams.length()];
                    String[] camStatus = new String[cams.length()];
                    String[] camLoc = new String[cams.length()];
                    String[] camImg = new String[cams.length()];
                    Log.d("Array data cams rec", String.valueOf(cams));

                    //ArrayList<String> myId = new ArrayList<String>();
                    //ArrayList<String> myValues = new ArrayList<String>();

                    Log.d("Array data locs", String.valueOf(data));
                    for (int i = 0; i < cams.length(); i++) {
                        //myId.add(location_id);
                        //myValues.add(location_name);

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
                    //RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.mContex,myValues,myId);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.mContex,camId,camName,camLoc,camImg);
                    RecyclerView myView =  (RecyclerView) mContex.findViewById(R.id.recyclerview);
                    myView.setHasFixedSize(true);
                    myView.setAdapter(adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(this.mContex);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    myView.setLayoutManager(llm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String getData(String cari) throws JSONException {
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
