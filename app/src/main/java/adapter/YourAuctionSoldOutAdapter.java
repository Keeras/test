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

public class YourAuctionSoldOutAdapter extends RecyclerView.Adapter<YourAuctionSoldOutAdapter.YourAuctionSoldOutViewHolder> {

    Context context;
    List<Bids> bidsList;

    public YourAuctionSoldOutAdapter(Context context, List<Bids> bidsList) {
        this.context = context;
        this.bidsList = bidsList;
    }

    @NonNull
    @Override
    public YourAuctionSoldOutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.yourauctionsoldout_layout,viewGroup,false);
        return new YourAuctionSoldOutAdapter.YourAuctionSoldOutViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull YourAuctionSoldOutViewHolder yourAuctionSoldOutViewHolder, int i) {
        final Bids bids = bidsList.get(i);
        String imgPath = Url.BASE_URL + "uploads/" + bids.getBidImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            yourAuctionSoldOutViewHolder.yourAuctionSoldOutBidsImgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        yourAuctionSoldOutViewHolder.tvCYourAuctionSoldOutBidsTitle.setText(bids.getBidTitle());
        yourAuctionSoldOutViewHolder.tvYourAuctionSoldOutBidsPrice.setText(String.valueOf(bids.getBidAmount()));
        yourAuctionSoldOutViewHolder.tvYourAuctionSoldOutMarketValue.setText(String.valueOf(bids.getMarketValue()));

//        yourAuctionOngoingViewHolder.yourAuctionOngoingBidsImgPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, YourAuctionOngoingDescriptionActivity.class);
//                intent.putExtra("bidImage",bids.getBidImage());
//                intent.putExtra("bidTitle",bids.getBidTitle());
//                intent.putExtra("bidPrice",bids.getStartingPrice());
//                intent.putExtra("maxPrice",bids.getMaxPrice());
//                intent.putExtra("marketValue",bids.getMarketValue());
//                intent.putExtra("endingDate",String.valueOf(bids.getEndingDate()));
//                intent.putExtra("category",bids.getCategory());
//                intent.putExtra("bidId",bids.getBidId());
//                context.startActivity(intent);
//            }
//        });
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    @Override
    public int getItemCount() {
        return bidsList.size();
    }

    public class YourAuctionSoldOutViewHolder extends RecyclerView.ViewHolder{
        ImageView yourAuctionSoldOutBidsImgPhoto;
        TextView tvCYourAuctionSoldOutBidsTitle, tvYourAuctionSoldOutBidsPrice, tvYourAuctionSoldOutMarketValue;

        public YourAuctionSoldOutViewHolder(@NonNull View bidView) {
            super(bidView);
            yourAuctionSoldOutBidsImgPhoto = itemView.findViewById(R.id.yourAuctionSoldOutBidsImgPhoto);
            tvCYourAuctionSoldOutBidsTitle = itemView.findViewById(R.id.tvCYourAuctionSoldOutBidsTitle);
            tvYourAuctionSoldOutBidsPrice = itemView.findViewById(R.id.tvYourAuctionSoldOutBidsPrice);
            tvYourAuctionSoldOutMarketValue = itemView.findViewById(R.id.tvYourAuctionSoldOutMarketValue);
        }
    }
}
