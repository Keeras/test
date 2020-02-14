package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionsystemapplication.BidDescriptionActivity;
import com.example.auctionsystemapplication.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Bids;
import url.Url;

public class BidsAdapter  extends RecyclerView.Adapter<BidsAdapter.BidsViewHolder>{
    Context context;
    List<Bids> bidsList;

    public BidsAdapter(Context context, List<Bids> bidsList) {
        this.context = context;
        this.bidsList = bidsList;
    }

    @NonNull
    @Override
    public BidsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bids_layout,viewGroup,false);
        return new BidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidsViewHolder bidsViewHolder, int i) {
        final Bids bids = bidsList.get(i);
        String imgPath = Url.BASE_URL + "uploads/" + bids.getBidImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            bidsViewHolder.imgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        bidsViewHolder.tvBidsTitle.setText(bids.getBidTitle());
      bidsViewHolder.tvBidsPrice.setText(String.valueOf(bids.getBidAmount()));
        bidsViewHolder.tvMArketValue.setText(String.valueOf(bids.getMarketValue()));

        bidsViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BidDescriptionActivity.class);
                intent.putExtra("bidImage",bids.getBidImage());
                intent.putExtra("bidTitle",bids.getBidTitle());
                intent.putExtra("bidPrice",bids.getBidAmount());
                intent.putExtra("maxPrice",bids.getMaxPrice());
                intent.putExtra("marketValue",bids.getMarketValue());
                intent.putExtra("endingDate",String.valueOf(bids.getEndingDate()));
                intent.putExtra("category",bids.getCategory());
                intent.putExtra("bidId",bids.getBidID());
//                Toast.makeText(context, String.valueOf(bids.getEndingDate()), Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });

        bidsViewHolder.btnOngoingBidFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BidDescriptionActivity.class);
                intent.putExtra("bidImage",bids.getBidImage());
                intent.putExtra("bidTitle",bids.getBidTitle());
                intent.putExtra("bidPrice",bids.getBidAmount());
                intent.putExtra("maxPrice",bids.getMaxPrice());
                intent.putExtra("marketValue",bids.getMarketValue());
                intent.putExtra("endingDate",String.valueOf(bids.getEndingDate()));
                intent.putExtra("category",bids.getCategory());
                intent.putExtra("bidId",bids.getBidID());
//                Toast.makeText(context, String.valueOf(bids.getEndingDate()), Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
    }
    private void OpenBidDescriptionActivity()
    {

    }
    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int getItemCount() {
        return bidsList.size();
    }

    public class BidsViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvBidsTitle, tvBidsPrice, tvMArketValue;
        Button btnOngoingBidFight;

        public BidsViewHolder(@NonNull View bidView) {
            super(bidView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            tvBidsTitle = itemView.findViewById(R.id.tvBidsTitle);
            tvBidsPrice = itemView.findViewById(R.id.tvBidsPrice);
            tvMArketValue = itemView.findViewById(R.id.tvMarketValue);
            btnOngoingBidFight = itemView.findViewById(R.id.btnOngoingBidFight);
        }
    }
}
