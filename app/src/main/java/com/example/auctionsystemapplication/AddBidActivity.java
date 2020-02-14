package com.example.auctionsystemapplication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auctionsystemapplication.BLL.AddBidBLL;
import com.example.auctionsystemapplication.BLL.LoginBLL;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import auctionsystemapi.AuctionSystemAPI;
import createChannel.CreateChannel;
import model.Bids;
import model.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import url.Url;

public class AddBidActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
        private Button  btnCreate;
    private EditText etTitle, etStartingPrice, etMaxPrice, etMarketPrice, etCategory;
    private TextView  tvEndingDate;
    private ImageView imageview;
    String imagepath;
    private String imageName;

    private int id =1;

    CreateChannel createChannel = new CreateChannel(this);
    private NotificationManagerCompat notificationManagerCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
        imageview = findViewById(R.id.imageview);

        btnCreate = findViewById(R.id.btnCreate);
        etTitle = findViewById(R.id.etTitle);
        etStartingPrice = findViewById(R.id.etStartingPrice);
        etMaxPrice = findViewById(R.id.etMaxPrice);
        etMarketPrice = findViewById(R.id.etMarketPrice);

        tvEndingDate = findViewById(R.id.tvEndingDate);
        etCategory = findViewById(R.id.etCategory);

        notificationManagerCompat = NotificationManagerCompat.from(this);

//        channel to handle notification
        createChannel.createChannel();
        //to access a camera we need a user permission
        checkPermission();
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });



        tvEndingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

    }

    private void Save() {
        if(validate()) {
            SaveImageOnly();
            final AddBidBLL bll = new AddBidBLL(1,imageName,etTitle.getText().toString(),Integer.parseInt(etStartingPrice.getText().toString()),Integer.parseInt(etMaxPrice.getText().toString()),Integer.parseInt(etMarketPrice.getText().toString()),tvEndingDate.getText().toString(),etCategory.getText().toString(),0,"Ongoing");
            com.example.auctionsystemapplication.StrickMode.StrictMode.StrictMode();

            if(bll.checkAddBid()){
                Toast.makeText(AddBidActivity.this, tvEndingDate.getText().toString(), Toast.LENGTH_LONG).show();
                addItemNotification();
                Toast.makeText(AddBidActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddBidActivity.this, UserDashboardActivity.class);
                  startActivity(intent);
            }
            else {
                Toast.makeText(AddBidActivity.this, "Error " , Toast.LENGTH_LONG).show();
    }

        }
    }

    private boolean validate() {
        boolean flag = true;
        if(TextUtils.isEmpty(etTitle.getText().toString())){
            etTitle.setError("Place Select Your Title");
            etTitle.requestFocus();
            flag= false;
        }
        else if(TextUtils.isEmpty(etStartingPrice.getText().toString())){
            etStartingPrice.setError("Place Select Your Starting Price");
            etStartingPrice.requestFocus();
            flag= false;
        }
        else if(TextUtils.isEmpty(etMaxPrice.getText().toString())){
            etMaxPrice.setError("Place Select Your Max Price");
            etMaxPrice.requestFocus();
            flag= false;
        }
        else if(TextUtils.isEmpty(etMarketPrice.getText().toString())){
            etMarketPrice.setError("Place Select Your Market Value");
            etMarketPrice.requestFocus();
            flag= false;
        }
        else if(TextUtils.isEmpty(tvEndingDate.getText().toString())){
            tvEndingDate.setError("Place Select Your Ending Date");
            tvEndingDate.requestFocus();
            flag= false;
        }
        else if(Integer.parseInt(etStartingPrice.getText().toString())>Integer.parseInt(etMaxPrice.getText().toString())){
            etStartingPrice.setError("Your Starting Price must be less than your max price");
            etStartingPrice.requestFocus();
            flag=false;
        }

        else if(Integer.parseInt(etMaxPrice.getText().toString())>Integer.parseInt(etMarketPrice.getText().toString())){
            etMaxPrice.setError("Your Max Price must be less than your MArket price");
            etMaxPrice.requestFocus();
            flag=false;
        }
        return  flag;
    }

    private void SaveImageOnly() {
        File file = new File(imagepath);
        Toast.makeText(this,""+file,Toast.LENGTH_LONG).show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        AuctionSystemAPI auctionSystemAPI = Url.getInstance().create(AuctionSystemAPI.class);
        Call<ImageResponse> responseBodyCall = auctionSystemAPI.uploadImage(Url.Token,body);

        StrictMode();

        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            //After saving an image, retrieve the current name of the image
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this,""+imageName,Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK); //to browse image
        intent.setType("image/*"); //user now can only select the image
        startActivityForResult(intent,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data==null){
                Toast.makeText(this,"Please Select an image",Toast.LENGTH_LONG).show();
            }
        }
        Uri uri = data.getData();
        imagepath = getRealPathFromUri(uri);
        previewImage(imagepath); //after getting imagepath display it in imageview
    }



    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();;
        return result;
    }

    private void previewImage(String imagepath) {
        File imgFlie = new File(imagepath);
        if(imgFlie.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFlie.getAbsolutePath());
            imageview.setImageBitmap(myBitmap);

        }
    }

    private void loadDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,year, month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
        datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis()-1000)+7*24*60*60*1000);
        datePickerDialog.show();
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },0);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String stringMonth = convertToMonth(month);
        String date =  year  + "-" + stringMonth + "-" + dayOfMonth;
        tvEndingDate.setText(date);
    }

    private String convertToMonth(int month){
        String textMonth="";
        switch (month){
            case 0:
                textMonth = "01";
                break;
            case 1:
                textMonth = "02";
                break;
            case 2:
                textMonth = "03";
                break;
            case 3:
                textMonth = "04";
                break;
            case 4:
                textMonth = "05";
                break;
            case 5:
                textMonth = "06";
                break;
            case 6:
                textMonth = "07";
                break;
            case 7:
                textMonth= "08";
                break;
            case 8:
                textMonth = "09";
                break;
            case 9:
                textMonth = "10";
                break;
            case 10:
                textMonth = "11";
                break;
            case 11:
                textMonth = "12";
                break;
        }
        return textMonth;

    }
    public void addItemNotification() {
        Notification notification = new NotificationCompat.Builder(this,CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_add_circle_outline_black_24dp)
                .setContentTitle("Item Added")
                .setContentText("Item Added successfully")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(id, notification);
        id++;
    }
}
