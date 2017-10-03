package lampung.dispenda.cctv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.module.Arsip_cam;

/**
 * Created by Chandra on 7/30/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private final String[] camId;
    private final String[] camName;
    private final String[] camLoc;
    private final String[] camImg;

    public RecyclerViewAdapter(Context context, String[] camId, String[] camName, String[] camLoc, String[] camImg) {
        this.camId= camId;
        this.camName= camName;
        this.camLoc= camLoc;
        this.camImg= camImg;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        //listItem.setOnClickListener();
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.camIdText.setText(camId[position]);
        holder.camNameText.setText(camName[position]);
        holder.locNameText.setText(camLoc[position]);
        holder.camImgText.setText(camImg[position]);
        Picasso.with(mContext).load(camImg[position])
                .into(holder.camImggbr, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        /*if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }*/
                    }
                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return camId.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView camIdText;
        private TextView locNameText;
        private TextView camNameText;
        private TextView camImgText;
        private ImageView camImggbr;
        public MyViewHolder(View itemView) {
            super(itemView);
            camIdText = (TextView)itemView.findViewById(R.id.myId);
            locNameText = (TextView)itemView.findViewById(R.id.text_cardview);
            camNameText = (TextView)itemView.findViewById(R.id.text_camname);
            camImgText = (TextView)itemView.findViewById(R.id.imgText);
            camImggbr = (ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, Arsip_cam.class);
                    intent.putExtra("camId", String.valueOf(camIdText.getText()));
                    intent.putExtra("camName", String.valueOf(camNameText.getText()));
                    intent.putExtra("camLoc", String.valueOf(locNameText.getText()));
                    intent.putExtra("camImg", String.valueOf(camImgText.getText()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
