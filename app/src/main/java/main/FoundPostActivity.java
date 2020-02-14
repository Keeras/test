package main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_harayo.MainBoard;
import com.example.project_harayo.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import api.PostAPI;
import api.UserApi;
import model.FoundPostModel;
import model.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import strictmode.StrictModeClass;
import url.Url;


public class FoundPostActivity extends AppCompatActivity {
    private EditText obName, obColor, obDescription, fnLandmark, fnCity, reLandmark, reCity;
    private Button btnPost;
    private ImageView BackArrow, ivDisplay;
    String imagePath;
    private String image="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_found_post);

        obName = findViewById(R.id.obName);
        obColor = findViewById(R.id.obColor);
        obDescription = findViewById(R.id.obDescription);
        fnLandmark = findViewById(R.id.fnLandmark);
        fnCity = findViewById(R.id.fnCity);
        reLandmark = findViewById(R.id.reLandmark);
        reCity = findViewById(R.id.reCity);
        btnPost = findViewById(R.id.btnPost);
        BackArrow = findViewById(R.id.ivBackArrow);
        ivDisplay = findViewById(R.id.ivRUpload);

//        btnCapture = findViewById(R.id.btnCapture);
//
//        checkPermission();


        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoundPostActivity.this, MainBoard.class);
                startActivity(intent);
                finish();
            }
        });

        ivDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

//        btnCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadCamera();
//            }
//
//            private void loadCamera() {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, 0);
//                }
//            }
//        });
//
//        private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]
//                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//        }
//    }

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
                saveFound();
            }
        });
    }
//
    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        ivDisplay.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("myfile",
                file.getName(), requestBody);

        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
        Call<ImageResponse> responseBodyCall = postAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            image = imageResponseResponse.body().getFilename();
//            Toast.makeText(this, "Image inserted" + image, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void saveFound() {
        String name = obName.getText().toString();
        String color = obColor.getText().toString();
        String description = obDescription.getText().toString();
        String fnlandmark = fnLandmark.getText().toString();
        String fncity = fnCity.getText().toString();
        String relandmark = reLandmark.getText().toString();
        String recity = reCity.getText().toString();
//        if (name.isEmpty()){
//            obName.setError("Please Enter Object Name");
//        }else if (color.isEmpty()){
//            obColor.setError("Please Enter Object Color or Type");
//        }else if (description.isEmpty()){
//            obDescription.setError("Please Enter Object Description");
//        }else if (fnlandmark.isEmpty()){
//            fnLandmark.setError("Please Enter Found Landmark");
//        }else if (fncity.isEmpty()){
//            fnCity.setError("Please Enter Found City");
//        }else if (relandmark.isEmpty()){
//            reLandmark.setError("Please Enter Returning Landmark");
//        }else if (recity.isEmpty()){
//            reCity.setError("Please Enter Returning City");
//        }

        FoundPostModel foundPostModel = new FoundPostModel(name, color, description, fnlandmark, fncity, relandmark, recity, image);
        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
        Call<Void> responseCall = postAPI.foundpost(foundPostModel);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FoundPostActivity.this, "Successfully Added Found Item", Toast.LENGTH_LONG).show();
////                    return;
                    obName.setText("");
                    obColor.setText("");
                    obDescription.setText("");
                    fnLandmark.setText("");
                    fnCity.setText("");
                    reLandmark.setText("");
                    reCity.setText("");
                    ivDisplay.setImageResource(R.drawable.upload_image_icon);
                } else {
                    Toast.makeText(FoundPostActivity.this, "Failed to Add Item", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FoundPostActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }









}
