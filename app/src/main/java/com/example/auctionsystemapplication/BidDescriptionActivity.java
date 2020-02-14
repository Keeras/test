package com.example.auctionsystemapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import auctionsystemapi.AuctionSystemAPI;
import model.Bids;
import model.Bids_Fight;
import model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import url.Url;

public class BidDescriptionActivity extends AppCompatActivity {
    int bidId;
    private EditText etDesPlaceYourBid;
    private Button btnDesAddAmount;
    private ImageView ivDesBidProfile;
    private TextView tvDesBidTitle,tvDesBidPrice,tvDesBidMaxPrice, tvDesBidMarketValue, tvDesBidEndingDate, tvDesBidCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_description);

        etDesPlaceYourBid = findViewById(R.id.etDesPlaceYourBid);
        btnDesAddAmount = findViewById(R.id.btnDesAddAmount);
        ivDesBidProfile = findViewById(R.id.ivDesBidProfile);
        tvDesBidTitle = findViewById(R.id.tvDesBidTitle);
        tvDesBidPrice = findViewById(R.id.tvDesBidPrice);
        tvDesBidMaxPrice = findViewById(R.id.tvDesBidMaxPrice);
        tvDesBidMarketValue = findViewById(R.id.tvDesBidMarketValue);
        tvDesBidEndingDate = findViewById(R.id.tvDesBidEndingDate);
        tvDesBidCategory = findViewById(R.id.tvDesBidCategory);

        StrictMode();
        URL url = null;
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            ivDesBidProfile.setImageResource(bundle.getInt("bidImage"));
            try {
                url = new URL("http://10.0.2.2:3000/uploads/"+bundle.getString("bidImage"));
                ivDesBidProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            tvDesBidTitle.setText(bundle.getString("bidTitle"));
            tvDesBidPrice.setText(String.valueOf(bundle.getInt("bidPrice")));
            tvDesBidMaxPrice.setText(String.valueOf(bundle.getInt("maxPrice")));
            tvDesBidMarketValue.setText(String.valueOf(bundle.getInt("marketValue")));
            tvDesBidEndingDate.setText(bundle.getString("endingDate"));
            tvDesBidCategory.setText(bundle.getString("category"));
            String bid_Id= String.valueOf(bundle.getInt("bidId"));
            bidId=Integer.parseInt(bid_Id);
            Toast.makeText(BidDescriptionActivity.this,"Bid Id: "+bidId,Toast.LENGTH_LONG).show();
        }

        btnDesAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        AddAmount();

            }
        });
    }

    private void AddAmount() {
        if(validate()){
            int bidAmount = Integer.parseInt(etDesPlaceYourBid.getText().toString());

            Bids_Fight bids_fight = new Bids_Fight(bidId,Url.userId,bidAmount);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            AuctionSystemAPI auctionSystemAPI = retrofit.create(AuctionSystemAPI.class);

            Call<model.Response> bidsCall = auctionSystemAPI.addBidsFight(Url.Token,bids_fight);

            bidsCall.enqueue(new Callback<model.Response>() {
                @Override
                public void onResponse(Call<model.Response> call, Response<model.Response> response) {
                    if (!response.isSuccessful()){

                        Toast.makeText(BidDescriptionActivity.this,"Code" + response.code(),Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(BidDescriptionActivity.this, "Successfully Added your bid",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BidDescriptionActivity.this,BidsActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<model.Response> call, Throwable t) {
                    Toast.makeText(BidDescriptionActivity.this,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });




        }

    }


    private boolean validate() {
        boolean flag = true;
        if(TextUtils.isEmpty(etDesPlaceYourBid.getText().toString())){
            etDesPlaceYourBid.setError("Place Your Bid First");
            etDesPlaceYourBid.requestFocus();
            flag= false;
        }
        else if(Integer.parseInt(etDesPlaceYourBid.getText().toString())<=Integer.parseInt(tvDesBidPrice.getText().toString())){
            etDesPlaceYourBid.setError("Please select higher amount than Bid Price and lower than Bid Max Price");
            etDesPlaceYourBid.requestFocus();
            flag=false;
        }
        return  flag;
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    }

