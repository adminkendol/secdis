package lampung.dispenda.cctv.child;

/**
 * Created by Chandra on 5/13/2016.
 * Function Activity Menu child CCTV
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lampung.dispenda.cctv.R;

public class ChildListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int HIGHLIGHT_COLOR = 0x999be6ff;
    // list of data items
    ArrayList<ListData> mDataList = new ArrayList<ListData>();
    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundles = getIntent().getExtras();
        String title = bundles.getString("title");
        String cd = bundles.getString("cd");
        String gt = bundles.getString("gt");
        String loc_id = bundles.getString("loc_id");
        Log.d("data CD", "ISI:" + cd);
        setContentView(R.layout.child_activity_list);
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

        JSONArray daftarChild = null;
        try {
            daftarChild = new JSONArray(cd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //String [] sData=null;
        for (int i = 0; i < daftarChild.length() ; i++){
            JSONObject c = null;
            try {
                c = daftarChild.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("data name", "isi:" + c);
            try {
                int no = Integer.parseInt(c.getString("menu_id"));
                Log.d("data NOCD", "ISI:" + no);
                Log.d("data GT", "ISI:" + gt + "|" + loc_id);

                /*if(gt.equals("3")){
                    if(c.getString("menu_id").equals(loc_id)){
                        String [] sData= {c.getString("menu_id"), c.getString("menu_name"), c.getString("mob_href")};
                        mDataList.add(i, new ListData(sData));
                    }
                }else {
                    String [] sData = {c.getString("menu_id"), c.getString("menu_name"), c.getString("mob_href")};
                    mDataList.add(i, new ListData(sData));
                }*/
                String [] sData= {c.getString("menu_id"), c.getString("menu_name"), c.getString("mob_href")};
                mDataList.add(i, new ListData(sData));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();
                //.roundRect(3);

        /*
        Intent intent = getIntent();
        int type = intent.getIntExtra(Child_menu.TYPE, DrawableProvider.SAMPLE_RECT);

        // initialize the builder based on the "TYPE"
        switch (type) {
            case DrawableProvider.SAMPLE_RECT:
                mDrawableBuilder = TextDrawable.builder()
                        .rect();
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT:
                mDrawableBuilder = TextDrawable.builder()
                        .roundRect(10);
                break;
            case DrawableProvider.SAMPLE_ROUND:
                mDrawableBuilder = TextDrawable.builder()
                        .round();
                break;
            case DrawableProvider.SAMPLE_RECT_BORDER:
                mDrawableBuilder = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .endConfig()
                        .rect();
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
                mDrawableBuilder = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .endConfig()
                        .roundRect(10);
                break;
            case DrawableProvider.SAMPLE_ROUND_BORDER:
                mDrawableBuilder = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .endConfig()
                        .round();
                break;
        }
        */
        // init the list view and its adapter
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SampleAdapter());
        listView.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView judul = (TextView) view.findViewById(R.id.textView);
        TextView textView = (TextView) view.findViewById(R.id.menu_href);
        TextView menu_id = (TextView) view.findViewById(R.id.menu_id);
        String title = judul.getText().toString();
        String text = textView.getText().toString();
        String IDS = menu_id.getText().toString();
        Log.d("data STRINGCD", "ISI:" + text);
        String page = null;
        if(text.equals("cctv")){
            page = "ListCctv";
        }else{
            page = text;
        }
        //Intent intent = new Intent(this, page);
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName("lampung.dispenda.cctv."+page));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        intent.putExtra("id", "3");
        intent.putExtra("loc", IDS);
        intent.putExtra("title", title);
        startActivity(intent);
    }



    private class SampleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public ListData getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ChildListActivity.this, R.layout.child_list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData item = getItem(position);
            final Drawable drawable = item.getDrawable();
            holder.imageView.setImageDrawable(drawable);
            holder.textView.setText(item.getLabel());
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
                    null,
                    getResources().getDrawable(R.drawable.ic_action_next_item),
                    null);
            // provide support for selected state
            updateCheckedState(holder, item);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when the image is clicked, update the selected state
                    ListData data = getItem(position);
                    data.setChecked(!data.isChecked);
                    updateCheckedState(holder, data);
                }
            });
            holder.textView.setText(item.data);
            holder.textId.setText(item.dataId);
            holder.textHref.setText(item.dataHref);
            return convertView;
        }

        private void updateCheckedState(ViewHolder holder, ListData item) {
            if (item.isChecked) {
                holder.imageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
                holder.view.setBackgroundColor(HIGHLIGHT_COLOR);
                holder.checkIcon.setVisibility(View.VISIBLE);
            }
            else {
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)), mColorGenerator.getColor(item.data));
                holder.imageView.setImageDrawable(drawable);
                holder.view.setBackgroundColor(Color.TRANSPARENT);
                holder.checkIcon.setVisibility(View.GONE);
            }
        }
    }

    private static class ViewHolder {

        private View view;
        private ImageView imageView;
        private TextView textView;
        private TextView textId;
        private TextView textHref;
        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            textId = (TextView) view.findViewById(R.id.menu_id);
            textHref = (TextView) view.findViewById(R.id.menu_href);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        }
    }

    private static class ListData {
        private String data;
        private String dataId;
        private String dataHref;

        private String label;

        private Drawable drawable;

        private int navigationInfo;

        private boolean isChecked;
        public ListData(String[] data) {
            Log.d("GET DATA CHILD:", String.valueOf(data));
            this.data = data[1];
            this.dataId = data[0];
            this.dataHref = data[2];
        }
        public ListData(String label, Drawable drawable, int navigationInfo) {
            this.label = label;
            this.drawable = drawable;
            this.navigationInfo = navigationInfo;
        }

        public String getLabel() {
            return label;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public int getNavigationInfo() {
            return navigationInfo;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }


    }
}
