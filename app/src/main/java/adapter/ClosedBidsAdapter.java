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

import com.example.auctionsystemapplication.BidDescriptionActivity;
import com.example.auctionsystemapplication.ClosedBidDescriptionActivity;
import com.example.auctionsystemapplication.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Bids;
import url.Url;

public class ClosedBidsAdapter extends RecyclerView.Adapter<ClosedBidsAdapter.ClosedBidsViewHolder> {
    Context context;
    List<Bids> bidsList;

    public ClosedBidsAdapter(Context context, List<Bids> bidsList) {
        this.context = context;
        this.bidsList = bidsList;
    }

    @NonNull
    @Override
    public ClosedBidsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.closedbids_layout,viewGroup,false);
        return new ClosedBidsAdapter.ClosedBidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClosedBidsViewHolder closedBidsViewHolder, int i) {
        final Bids bids = bidsList.get(i);
        String imgPath = Url.BASE_URL + "uploads/" + bids.getBidImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            closedBidsViewHolder.closedBidsImgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        closedBidsViewHolder.tvClosedBidsBidsTitle.setText(bids.getBidTitle());
        closedBidsViewHolder.tvClosedBidsBidsPrice.setText(String.valueOf(bids.getStartingPrice()));
        closedBidsViewHolder.tvClosedBidsMarketValue.setText(String.valueOf(bids.getMarketValue()));

        closedBidsViewHolder.closedBidsImgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClosedBidDescriptionActivity.class);
                intent.putExtra("bidImage",bids.getBidImage());
                intent.putExtra("bidTitle",bids.getBidTitle());
                intent.putExtra("bidPrice",bids.getStartingPrice());
                intent.putExtra("maxPrice",bids.getMaxPrice());
                intent.putExtra("marketValue",bids.getMarketValue());
                intent.putExtra("endingDate",String.valueOf(bids.getEndingDate()));
                intent.putExtra("category",bids.getCategory());
                intent.putExtra("bidId",bids.getBidId());
//                Toast.makeText(context, String.valueOf(bids.getEndingDate()), Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
        closedBidsViewHolder.btnStartYourBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClosedBidDescriptionActivity.class);
                intent.putExtra("bidImage",bids.getBidImage());
                intent.putExtra("bidTitle",bids.getBidTitle());
                intent.putExtra("bidPrice",bids.getStartingPrice());
                intent.putExtra("maxPrice",bids.getMaxPrice());
                intent.putExtra("marketValue",bids.getMarketValue());
                intent.putExtra("endingDate",String.valueOf(bids.getEndingDate()));
                intent.putExtra("category",bids.getCategory());
                intent.putExtra("bidId",bids.getBidId());
//                Toast.makeText(context, String.valueOf(bids.getEndingDate()), Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int getItemCount() {
        return bidsList.size();
    }

    public class ClosedBidsViewHolder extends RecyclerView.ViewHolder{
        ImageView closedBidsImgPhoto;
        TextView tvClosedBidsBidsTitle, tvClosedBidsBidsPrice, tvClosedBidsMarketValue;
        Button btnStartYourBid;

        public ClosedBidsViewHolder(@NonNull View bidView) {
            super(bidView);
            closedBidsImgPhoto = itemView.findViewById(R.id.closedBidsImgPhoto);
            tvClosedBidsBidsTitle = itemView.findViewById(R.id.tvClosedBidsBidsTitle);
            tvClosedBidsBidsPrice = itemView.findViewById(R.id.tvClosedBidsBidsPrice);
            tvClosedBidsMarketValue = itemView.findViewById(R.id.tvClosedBidsMarketValue);
            btnStartYourBid = itemView.findViewById(R.id.btnStartYourBid);
        }
    }
}
