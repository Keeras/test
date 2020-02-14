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

public class YourAuctionWinAdapter  extends RecyclerView.Adapter<YourAuctionWinAdapter.YourAuctionWinViewHolder> {

    Context context;
    List<Bids> bidsList;

    public YourAuctionWinAdapter(Context context, List<Bids> bidsList) {
        this.context = context;
        this.bidsList = bidsList;
    }

    @NonNull
    @Override
    public YourAuctionWinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.yourauctionwin_layout,viewGroup,false);
        return new YourAuctionWinAdapter.YourAuctionWinViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull YourAuctionWinViewHolder yourAuctionWinViewHolder, int i) {
        final Bids bids = bidsList.get(i);
        String imgPath = Url.BASE_URL + "uploads/" + bids.getBidImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            yourAuctionWinViewHolder.yourAuctionWinBidsImgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        yourAuctionWinViewHolder.tvCYourAuctionWinBidsTitle.setText(bids.getBidTitle());
         yourAuctionWinViewHolder.tvYourAuctionWinMarketValue.setText(String.valueOf(bids.getBidAmount()));

    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int getItemCount() {
        return bidsList.size();
    }


    public class YourAuctionWinViewHolder extends RecyclerView.ViewHolder{
        ImageView yourAuctionWinBidsImgPhoto;
        TextView tvCYourAuctionWinBidsTitle, tvYourAuctionWinMarketValue;

        public YourAuctionWinViewHolder(@NonNull View bidView) {
            super(bidView);
            yourAuctionWinBidsImgPhoto = itemView.findViewById(R.id.yourAuctionWinBidsImgPhoto);
            tvCYourAuctionWinBidsTitle = itemView.findViewById(R.id.tvCYourAuctionWinBidsTitle);
            tvYourAuctionWinMarketValue = itemView.findViewById(R.id.tvYourAuctionWinMarketValue);
        }
    }
}
