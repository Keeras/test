package adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auctionsystemapplication.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Bids;
import url.Url;

public class SoldBidsAdapter extends RecyclerView.Adapter<SoldBidsAdapter.SoldBidsViewHolder> {
    Context context;
    List<Bids> bidsList;

    public SoldBidsAdapter(Context context, List<Bids> bidsList) {
        this.context = context;
        this.bidsList = bidsList;
    }

    @NonNull
    @Override
    public SoldBidsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.soldbids_layout,viewGroup,false);
        return new SoldBidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoldBidsViewHolder soldBidsViewHolder, int i) {
        final Bids bids = bidsList.get(i);
        String imgPath = Url.BASE_URL + "uploads/" + bids.getBidImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            soldBidsViewHolder.soldBidsImgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        soldBidsViewHolder.tvSoldBidsBidsTitle.setText(bids.getBidTitle());
        soldBidsViewHolder.tvSoldBidsBidsPrice.setText(String.valueOf(bids.getBidAmount()));
        soldBidsViewHolder.tvSoldBidsMarketValue.setText(String.valueOf(bids.getMarketValue()));
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    @Override
    public int getItemCount() {
        return bidsList.size();
    }

    public class SoldBidsViewHolder extends RecyclerView.ViewHolder{
        ImageView soldBidsImgPhoto;
        TextView tvSoldBidsBidsTitle, tvSoldBidsBidsPrice, tvSoldBidsMarketValue;

        public SoldBidsViewHolder(@NonNull View bidView) {
            super(bidView);
            soldBidsImgPhoto = itemView.findViewById(R.id.soldBidsImgPhoto);
            tvSoldBidsBidsTitle = itemView.findViewById(R.id.tvSoldBidsBidsTitle);
            tvSoldBidsBidsPrice = itemView.findViewById(R.id.tvSoldBidsBidsPrice);
            tvSoldBidsMarketValue = itemView.findViewById(R.id.tvSoldBidsMarketValue);
        }
    }
}
