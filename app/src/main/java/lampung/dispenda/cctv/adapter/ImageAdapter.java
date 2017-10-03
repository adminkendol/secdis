package lampung.dispenda.cctv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lampung.dispenda.cctv.R;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final String[] idArr;
	private final String[] nameArr;
	private final String[] ipArr;
	private final String[] stArr;
	private final String[] locArr;
	private final String[] imgArr;
	private final String[] liveArr;
	private final String[] recArr;
	LayoutInflater inflater;
	public ImageAdapter(Context context, String[] idArr, String[] nameArr, String[] ipArr, String[] stArr, String[] locArr, String[] imgArr, String[] liveArr, String[] recArr) {
		this.context = context;
		this.idArr = idArr;
		this.nameArr = nameArr;
		this.ipArr = ipArr;
		this.stArr = stArr;
		this.locArr = locArr;
		this.imgArr = imgArr;
		this.liveArr = liveArr;
		this.recArr = recArr;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.griditem, null);
		}
		TextView Id = (TextView) convertView.findViewById(R.id.camId);
		Id.setText(idArr[position]);
		TextView textView = (TextView) convertView.findViewById(R.id.camName);
		textView.setText(nameArr[position]);
		TextView textViewA = (TextView) convertView.findViewById(R.id.ipCam);
		textViewA.setText(ipArr[position]);
		TextView textViewB = (TextView) convertView.findViewById(R.id.locCam);
		textViewB.setText(locArr[position]);
		TextView textViewC = (TextView) convertView.findViewById(R.id.liveCam);
		textViewC.setText(liveArr[position]);
		TextView textViewD = (TextView) convertView.findViewById(R.id.recCam);
		textViewD.setText(recArr[position]);
		// set image based on selected text
		ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
		String mobile = imgArr[position];
		final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.loading);
		progressBar.setVisibility(View.VISIBLE);
		Picasso.with(context).load(mobile)
				.into(imageView, new com.squareup.picasso.Callback() {
					@Override
					public void onSuccess() {
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
						}
					}
					@Override
					public void onError() {

					}
				});

		return convertView;
	}
	@Override
	public int getCount() {
		return idArr.length;
	}
	@Override
	public Object getItem(int position) {
		return idArr[position];
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
}
