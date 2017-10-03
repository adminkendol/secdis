package lampung.dispenda.cctv.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.module.Setup_form_loc;
import lampung.dispenda.cctv.module.Setup_form_users;

/**
 * Created by sulei on 8/12/2015.
 */
public class GmailAdapter extends RecyclerView.Adapter<GmailAdapter.GmailVH> {
    private List<String> dataListA;
    private List<String> dataListB;
    private List<String> dataListC;
    private List<String> dataListD;
    private String letter;
    private Context context;
    private ColorGenerator generator = ColorGenerator.MATERIAL;


    /*int colors[] = {R.color.red, R.color.pink, R.color.purple, R.color.deep_purple,
            R.color.indigo, R.color.blue, R.color.light_blue, R.color.cyan, R.color.teal, R.color.green,
            R.color.light_green, R.color.lime, R.color.yellow, R.color.amber, R.color.orange, R.color.deep_orange};*/

    public GmailAdapter(Context context, List<String> listDataA, List<String> listDataB, List<String> listDataC, List<String> listDataD) {
        this.context = context;

        this.dataListA = listDataA;
        this.dataListB = listDataB;
        this.dataListC = listDataC;
        this.dataListD = listDataD;
    }

    @Override
    public GmailVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gmail_list_item, viewGroup, false);
        return new GmailVH(view);
    }

    @Override
    public void onBindViewHolder(GmailVH gmailVH, int i) {
        gmailVH.title.setText(dataListB.get(i));
        gmailVH.city.setText(dataListC.get(i));
        gmailVH.prov.setText(dataListD.get(i));
        gmailVH.locId.setText(dataListA.get(i));
//        Get the first letter of list item
        letter = String.valueOf(dataListB.get(i).charAt(0));

//        Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        gmailVH.letter.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return dataListA == null ? 0 : dataListA.size();
    }

    class GmailVH extends RecyclerView.ViewHolder {
        TextView title;
        TextView city;
        TextView prov;
        TextView locId;
        ImageView letter;

        public GmailVH(View itemView) {
            super(itemView);
            letter = (ImageView) itemView.findViewById(R.id.gmailitem_letter);
            title = (TextView) itemView.findViewById(R.id.gmailitem_title);
            city = (TextView) itemView.findViewById(R.id.city);
            prov = (TextView) itemView.findViewById(R.id.prov);
            locId = (TextView) itemView.findViewById(R.id.locId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Setup_form_loc.LocklikListener myListener = new Setup_form_loc.LocklikListener() {
                        @Override
                        public void onButtonClick() {
                            ((Activity)(context)).finish();
                            ((Activity)(context)).startActivity(((Activity)(context)).getIntent());
                        }
                    };
                    Setup_form_loc form = new Setup_form_loc(context,myListener, (String) locId.getText());
                    form.setTitle("FORM LOCATION");
                    form.show();
                }
            });
        }
    }

}


