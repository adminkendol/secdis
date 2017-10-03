package lampung.dispenda.cctv;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Chandra on 5/5/2016.
 * Function Adapter List CCTV
 */
public class ListAdapter  extends BaseAdapter {
    private Activity activity;
    private ArrayList<Cctv> data_cctv=new ArrayList<Cctv>();

    private static LayoutInflater inflater = null;

    public ListAdapter(Activity a, ArrayList<Cctv> d) {
        activity = a; data_cctv = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data_cctv.size();
    }
    public Object getItem(int position) {
        return data_cctv.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
        vi = inflater.inflate(R.layout.list, null);
        TextView id_cctv = (TextView) vi.findViewById(R.id.id_cctv);
        final TextView ip_cctv = (TextView) vi.findViewById(R.id.ip_cctv);
        TextView nama_cctv = (TextView) vi.findViewById(R.id.nama_cctv);
        TextView loc_cctv = (TextView) vi.findViewById(R.id.loc_cctv);
        TextView loc_list = (TextView) vi.findViewById(R.id.loc_list);
        TextView id_list = (TextView) vi.findViewById(R.id.id_list);
        ImageView capture = (ImageView) vi.findViewById(R.id.cap_img);
        Button show= (Button) vi.findViewById(R.id.button_v);
        final Cctv daftar_cctv = data_cctv.get(position);
        id_cctv.setText(daftar_cctv.getCctvId());
        ip_cctv.setText(daftar_cctv.getCctvIp());
        nama_cctv.setText(daftar_cctv.getCctvName());
        loc_cctv.setText(daftar_cctv.getLocCctv());
        loc_list.setText(daftar_cctv.getLocList());
        id_list.setText(daftar_cctv.getIdList());
        Log.i("show IMG", (daftar_cctv.getCapture()));



        if(daftar_cctv.getCctvStatus().equals("2")){
            show.setText("OFFLINE");
            show.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);builder.setTitle("Perhatian");
                    builder.setMessage("CCTV Offline");
                    builder.setIcon(R.drawable.ic_content_report);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            Toast.makeText(activity, "Silahkan pilih rekaman", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            });
        }else {
            show.setText("SHOW");
            show.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intentB;
                    intentB = new Intent(activity, Show.class);
                    intentB.putExtra("ip", daftar_cctv.getCctvIp());
                    intentB.putExtra("id", daftar_cctv.getIdList());
                    intentB.putExtra("loc", daftar_cctv.getLocList());
                    intentB.putExtra("loc_name", daftar_cctv.getLocCctv());
                    activity.startActivity(intentB);

                }
            });
        }
        new DownloadImageTask(capture).execute(daftar_cctv.getCapture());
        return vi;
    }



    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error-BITMAP", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }
}