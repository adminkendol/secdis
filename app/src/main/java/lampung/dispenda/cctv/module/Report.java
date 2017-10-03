package lampung.dispenda.cctv.module;

/**
 * Created by Chandra on 10/18/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lampung.dispenda.cctv.JSONParser;
import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.adapter.AdapterListView;
import lampung.dispenda.cctv.config.Basic;

public class Report extends AppCompatActivity {
    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS ="success";
    //final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String tglA;
    String gt;
    String loc_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        gt = bundle.getString("gt");
        loc_id = bundle.getString("loc_id");
        setContentView(R.layout.table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        setTitle(title);

        Date date = new Date();
        final EditText fromdate=(EditText)findViewById(R.id.fromdate);
        fromdate.setTextColor(Color.BLACK);
        String tgl = dateFormat.format(date);
        fromdate.setText(tgl);
        getDialogTime(fromdate, 1);
        Button cari =(Button)findViewById(R.id.buttonCari);
        cari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ReadDataTask m = (ReadDataTask) new ReadDataTask(fromdate.getText().toString()).execute();
            }
        });
        tglA= String.valueOf(fromdate.getText());
        ReadDataTask m= (ReadDataTask) new ReadDataTask(fromdate.getText().toString()).execute();

    }
    private void getDialogTime(final EditText src, final int i) {
        src.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
                LayoutInflater inflater = Report.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.form_tgl_notime, null);
                final CalendarView CV =(CalendarView) dialogView.findViewById(R.id.calendarView);
                builder.setView(dialogView);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long cv_Text = CV.getDate();
                        String cv_date = dateFormat.format(cv_Text);
                        src.setText(cv_date);
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
    }
    class ReadDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;


        String fromdate;
        public ReadDataTask(String fromdate) {
            this.fromdate=fromdate;

        }

        private class NexusTypes {
            private final String name;
            private final List<Nexus> list;

            NexusTypes(String name) {
                this.name = name;
                list = new ArrayList<Nexus>();
            }

            public int size() {
                return list.size();
            }

            public Nexus get(int i) {
                return list.get(i);
            }
        }

        private class Nexus {
            private final String[] data;
            private Nexus(String loc, String online, String offline) {
                data = new String[] {
                        loc,
                        online,
                        offline};
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Report.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... sText) {
            String returnResult = BuildReport(fromdate);
            return returnResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Basic config = new Basic();
            String url = config.getUrl();
            LinearLayout ll =(LinearLayout) findViewById(R.id.ll_users);
            if (result.equalsIgnoreCase("Exception Caught")) {
                Log.d("RESULT API", "data:" + String.valueOf(result) + ":END");
                Toast.makeText(Report.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(ll, "Load data gagal", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReadDataTask m= (ReadDataTask) new ReadDataTask(fromdate).execute();
                    }
                }).show();
                snackbar.show();
            }else if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(Report.this, "Data empty", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(ll, "Load data gagal", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReadDataTask m= (ReadDataTask) new ReadDataTask(fromdate).execute();
                    }
                }).show();
                snackbar.show();
            } else {

                TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
                BaseTableAdapter baseTableAdapter = new FamilyNexusAdapter(Report.this,result,fromdate);
                tableFixHeaders.setAdapter(baseTableAdapter);

            }
        }
        private String BuildReport(String tgl) {
            Basic config = new Basic();
            String url = config.getREPORT();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            if(gt.equals("3")){
                parameter.add(new BasicNameValuePair("loc_id", loc_id));
            }else{
                parameter.add(new BasicNameValuePair("loc_id", ""));
            }
            parameter.add(new BasicNameValuePair("date", tgl));
            Log.d("URL DATA REPORT", url);
            try {
                JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
                if(String.valueOf(json).equals("null")){
                    return "Exception Caught";
                }else {
                    Log.d("RESULT DATA REPORT", String.valueOf(json));
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray dataRep = json.getJSONArray("data");
                        return String.valueOf(dataRep);
                    } else {
                        return "no results";
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
                Log.d("URL DATA ERROR", String.valueOf(e));
                return "Exception Caught";
            }
            //return "Exception Caught";
        }
        public class FamilyNexusAdapter extends BaseTableAdapter {
            int rows;
            private final NexusTypes familys[];
            private final String headers[] = {
                    "Location",
                    "Online",
                    "Offline"
            };
            private final int[] widths = {
                    200,
                    100,
                    100,
            };
            private final float density;

            public FamilyNexusAdapter(Context context, String resREp, String tglA) {
                //String tgl ="2016-10-21";
                familys = new NexusTypes[] {
                        new NexusTypes(tglA),
                        //new NexusTypes("19 October 2016"),
                        //new NexusTypes("19 October 2016"),
                };
                density = context.getResources().getDisplayMetrics().density;
                //String resREp = BuildReport(tgl);
                JSONArray dataRep = null;
                try {
                    dataRep = new JSONArray(resREp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rows=dataRep.length();
                Log.d("ISI REP:", String.valueOf(dataRep));
                for (int i = 0; i < dataRep.length(); i++) {
                    try {
                        JSONArray b = dataRep.getJSONArray(i);
                        JSONObject c = b.getJSONObject(0);
                        String tot_h_up=c.getString("tot_h_up");
                        if(tot_h_up.equals("null")){
                            tot_h_up="0";
                        }
                        String tot_h_down=c.getString("tot_h_down");
                        if(tot_h_down.equals("null")){
                            tot_h_down="0";
                        }
                        String location_name=c.getString("location_name");
                        familys[0].list.add(new Nexus(location_name, tot_h_up+" Jam", tot_h_down+" Jam"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public int getRowCount() {
                Log.d("ROWS:", String.valueOf(rows));
                //+3
                return rows+1;
                //return 5;
            }

            @Override
            public int getColumnCount() {
                //return 6;
                return 2;
            }

            @Override
            public View getView(int row, int column, View convertView, ViewGroup parent) {
                final View view;
                switch (getItemViewType(row, column)) {
                    case 0:
                        view = getFirstHeader(row, column, convertView, parent);
                        break;
                    case 1:
                        view = getHeader(row, column, convertView, parent);
                        break;
                    case 2:
                        view = getFirstBody(row, column, convertView, parent);
                        break;
                    case 3:
                        view = getBody(row, column, convertView, parent);
                        break;
                    case 4:
                        view = getFamilyView(row, column, convertView, parent);
                        break;
                    default:
                        throw new RuntimeException("wtf?");
                }
                return view;
            }

            private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
                }
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
                return convertView;
            }

            private View getHeader(int row, int column, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
                }
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
                return convertView;
            }

            private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
                }
                convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
                return convertView;
            }

            private View getBody(int row, int column, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
                }
                convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
                return convertView;
            }

            private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
                }
                final String string;
                if (column == -1) {
                    string = getFamily(row).name;
                } else {
                    string = "";
                }
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
                return convertView;
            }

            @Override
            public int getWidth(int column) {
                return Math.round(widths[column + 1] * density);
            }

            @Override
            public int getHeight(int row) {
                final int height;
                if (row == -1) {
                    height = 35;
                } else if (isFamily(row)) {
                    height = 25;
                } else {
                    height = 45;
                }
                return Math.round(height * density);
            }

            @Override
            public int getItemViewType(int row, int column) {
                final int itemViewType;
                if (row == -1 && column == -1) {
                    itemViewType = 0;
                } else if (row == -1) {
                    itemViewType = 1;
                } else if (isFamily(row)) {
                    itemViewType = 4;
                } else if (column == -1) {
                    itemViewType = 2;
                } else {
                    itemViewType = 3;
                }
                return itemViewType;
            }

            private boolean isFamily(int row) {
                int family = 0;
                while (row > 0) {
                    row -= familys[family].size() + 1;
                    family++;
                }
                return row == 0;
            }

            private NexusTypes getFamily(int row) {
                int family = 0;
                while (row >= 0) {
                    row -= familys[family].size() + 1;
                    family++;
                }
                return familys[family - 1];
            }

            private Nexus getDevice(int row) {
                int family = 0;
                while (row >= 0) {
                    row -= familys[family].size() + 1;
                    family++;
                }
                family--;
                return familys[family].get(row + familys[family].size());
            }

            @Override
            public int getViewTypeCount() {
                return 5;
            }
        }
    }
}
