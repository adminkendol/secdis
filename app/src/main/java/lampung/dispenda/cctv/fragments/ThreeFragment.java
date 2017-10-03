package lampung.dispenda.cctv.fragments;

//import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.util.ArrayList;

import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.chart.Infoserver;
import lampung.dispenda.cctv.chart.custom.MyYAxisValueFormatter;


public class ThreeFragment extends Infoserver implements OnChartValueSelectedListener {
    protected BarChart mChart;
    private Typeface mTf;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_three, container, false);
        View view = inflater.inflate(R.layout.fragment_three,container,false);
        bindChartView();
        return view;
    }
    @SuppressLint("NewApi")
     @Override
     public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);
        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());
        Log.i("x-index",
                "low: " + mChart.getLowestVisibleXIndex() + ", high: "
                        + mChart.getHighestVisibleXIndex());
    }
    public void onNothingSelected() {
    };
    public void bindChartView(){
        new LongOperation(getActivity(), mChart).execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String[][]> {
        Activity mContex;
        ProgressDialog pDialog;

        public LongOperation(FragmentActivity activity, BarChart mChart) {
            this.mContex=activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.mContex);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String[][] doInBackground(String... params) {
            // Do here what you want Every thing process should be here only
            String[] info =null;
            String[] dash =null;
            try {
                info = infoServer();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                dash = dash();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[][] balik = {dash,info};
            return balik;
        }

        @Override
        protected void onPostExecute(String[][] result) {
            pDialog.dismiss();
            mChart = (BarChart) mContex.findViewById(R.id.chart1);
            mChart.setOnChartValueSelectedListener(ThreeFragment.this);
            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(true);
            mChart.setDescription("");
            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            mChart.setMaxVisibleValueCount(60);
            // scaling can now only be done on x- and y-axis separately
            mChart.setPinchZoom(false);
            mChart.setDrawGridBackground(false);
            // mChart.setDrawYLabels(false);
            mTf = Typeface.createFromAsset(mContex.getAssets(), "OpenSans-Regular.ttf");
            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setTextSize(13);
            xAxis.setDrawGridLines(true);
            xAxis.setSpaceBetweenLabels(1);

            YAxisValueFormatter custom = new MyYAxisValueFormatter();
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setLabelCount(8, false);
            leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setTextColor(Color.BLACK);
            leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setTypeface(mTf);
            rightAxis.setLabelCount(8, false);
            rightAxis.setValueFormatter(custom);
            rightAxis.setSpaceTop(15f);
            rightAxis.setTextColor(Color.BLACK);
            rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setTextColor(Color.WHITE);
            l.setXEntrySpace(4f);
            // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
            // "def", "ghj", "ikl", "mno" });
            // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
            // "def", "ghj", "ikl", "mno" });
            if((result[0].equals("Exception Caught"))||(result[1].length==1)){
                return;
            }
            try {
                setData(4, 50,result[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (IBarDataSet set : mChart.getData().getDataSets())
                ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);
            mChart.invalidate();
            mChart.animateXY(3000, 3000);
            // mChart.setDrawLegend(false);
            TextView infoA = (TextView) mContex.findViewById(R.id.info1);
            String[] info =null;
            info = result[1];
            String fon;
            String fonA;
            if (Integer.valueOf(info[6]) > 50){
                fon = "red";
            }else{
                fon = "black";
            }
            if (Integer.valueOf(info[8]) > 0){
                fonA = "red";
            }else{
                fonA = "black";
            }
            infoA.setText(Html.fromHtml("<html><body style='background:white'>OS : " + String.valueOf(info[0]) + "<br>" +
                    "Kernel : " + String.valueOf(info[1]) + "<br>" +
                    "Version : " + String.valueOf(info[2]) + "<br>" +
                    "Hostname : " + String.valueOf(info[3]) + "<br>" +
                    "Uptime : " + String.valueOf(info[4]) + "<br>" +
                    "CPU : " + String.valueOf(info[5]) + "<br>" +
                    "Temp : <font color='" + fon + "'>" + String.valueOf(info[6]) + "</font> Degree " + info[7] + "<br>" +
                    "Root Loggin : <font color='" + fonA + "'>" + String.valueOf(info[8]) + "</font> Users<br>" +
                    "</body></html>"));
            infoA.setMovementMethod(new ScrollingMovementMethod());
            //now you can see the result here
        }
        private void setData(int count, float range, String[] strings) throws JSONException {
            ArrayList<String> xVals = new ArrayList<String>();
            for (int i = 0; i < count; i++) {
                xVals.add(mBar[i % 4]);
            }
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            for (int i = 0; i < count; i++) {
                int vals =Integer.parseInt(String.valueOf(strings[i]));
                yVals1.add(new BarEntry(vals, i));
            }
            BarDataSet set1;
            if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
                set1.setYVals(yVals1);
                mChart.getData().setXVals(xVals);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                set1 = new BarDataSet(yVals1, "DataSet");
                set1.setBarSpacePercent(35f);
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);
                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);
                mChart.setData(data);
            }
        }
    }

}
