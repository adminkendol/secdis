package lampung.dispenda.cctv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.module.Setup_form_users;
import lampung.dispenda.cctv.module.Setup_form_users.myOnClickListener;
import lampung.dispenda.cctv.module.Setup_users;

/**
 * Created by Chandra on 10/2/2016.
 */
public class AdapterListView extends RecyclerView.Adapter<AdapterListView.MyViewHolder> {
    private Context mContext;
    private final String[] user_id;
    private final String[] user_fullname;
    private final String[] user_name;
    private final String[] group_type_id;
    private final String[] location_id;
    private final String[] active_id;
    private final String[] email;
    private final String[] phone;
    private final String[] mobile_phone;
    private final String[] dp_name;
    private final String[] status_name;
    private final String[] group_type_name;
    private final String[] location_name;

    public AdapterListView(Activity contex, String[] user_id,String[] user_fullname,String[] user_name,String[] group_type_id,String[] location_id,String[] active_id,String[] email,String[] phone,String[] mobile_phone,String[] dp_name,String[] status_name,String[] group_type_name,String[] location_name) {
        this.user_id=user_id;
        this.user_fullname=user_fullname;
        this.user_name=user_name;
        this.group_type_id=group_type_id;
        this.location_id=location_id;
        this.active_id=active_id;
        this.email=email;
        this.phone=phone;
        this.mobile_phone=mobile_phone;
        this.dp_name=dp_name;
        this.status_name=status_name;
        this.group_type_name=group_type_name;
        this.location_name=location_name;
        this.mContext = contex;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.listuser, parent, false);
        //listItem.setOnClickListener();
        return new MyViewHolder(listItem);

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.fullname.setText("Nama : "+user_fullname[position]);
        holder.username.setText("User login : "+user_name[position]);
        holder.group_type_name.setText("User type : "+group_type_name[position]);
        holder.userId.setText(user_id[position]);
        holder.fileText.setText(location_id[position]);
    }

    @Override
    public int getItemCount() {
        return user_id.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname;
        private TextView username;
        private TextView group_type_name;
        private TextView userId;
        private TextView fileText;
        public MyViewHolder(View itemView) {
            super(itemView);
            fullname = (TextView)itemView.findViewById(R.id.fullname);
            username = (TextView)itemView.findViewById(R.id.username);
            group_type_name = (TextView)itemView.findViewById(R.id.group_type_name);
            userId = (TextView)itemView.findViewById(R.id.userId);
            fileText = (TextView)itemView.findViewById(R.id.fileText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener myListener = new myOnClickListener() {
                        @Override
                        public void onButtonClick() {
                            ((Activity)(mContext)).finish();
                            ((Activity)(mContext)).startActivity(((Activity)(mContext)).getIntent());
                        }
                    };
                Setup_form_users form = new Setup_form_users(mContext,myListener, (String) userId.getText());
                form.setTitle("FORM USER");
                form.show();
                }
            });
        }
    }
}
