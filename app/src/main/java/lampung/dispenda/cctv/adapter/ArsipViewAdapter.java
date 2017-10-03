package lampung.dispenda.cctv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.Show;
import lampung.dispenda.cctv.module.Arsip_cam;

/**
 * Created by Chandra on 9/24/2016.
 */
public class ArsipViewAdapter extends RecyclerView.Adapter<ArsipViewAdapter.MyViewHolder> {
    private Context mContext;
    private final String[] rec_id;
    private final String[] rec_name;
    private final String[] camera_id;
    private final String[] location_name;
    private final String[] camera_name;
    private final String[] tgl;
    private final String[] jam;
    public ArsipViewAdapter(Activity contex, String[] rec_id, String[] rec_name, String[] camera_id, String[] location_name, String[] camera_name, String[] tgl, String[] jam) {
        this.rec_id= rec_id;
        this.rec_name= rec_name;
        this.camera_id= camera_id;
        this.location_name= location_name;
        this.camera_name= camera_name;
        this.tgl= tgl;
        this.jam= jam;
        this.mContext = contex;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.listarsip, parent, false);
        //listItem.setOnClickListener();
        return new MyViewHolder(listItem);

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_camname.setText(camera_name[position]);
        holder.text_tglview.setText(tgl[position]);
        holder.text_jamview.setText(jam[position]);
        holder.myId.setText(rec_id[position]);
        holder.fileText.setText(rec_name[position]);
    }

    @Override
    public int getItemCount() {
        return rec_id.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text_camname;
        private TextView text_tglview;
        private TextView text_jamview;
        private TextView myId;
        private TextView fileText;
        public MyViewHolder(View itemView) {
            super(itemView);
            text_camname = (TextView)itemView.findViewById(R.id.text_camname);
            text_tglview = (TextView)itemView.findViewById(R.id.text_tglview);
            text_jamview = (TextView)itemView.findViewById(R.id.text_jamview);
            myId = (TextView)itemView.findViewById(R.id.myId);
            fileText = (TextView)itemView.findViewById(R.id.fileText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentB = new Intent(mContext, Show.class);
                    intentB.putExtra("ip", "");
                    intentB.putExtra("live", "0");
                    intentB.putExtra("rec", ((TextView) v.findViewById(R.id.fileText)).getText());
                    mContext.startActivity(intentB);
                }
            });
        }
    }
}
