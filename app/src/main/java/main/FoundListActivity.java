package main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_harayo.MainBoard;
import com.example.project_harayo.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FoundAdapter;
import api.PostAPI;
import model.FoundPostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

public class FoundListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<FoundPostModel> foundlist = new ArrayList<>();
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_found_list);


        recyclerView = findViewById(R.id.rcFoundView);
        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
         Call<List<FoundPostModel>> responseCall = postAPI.getFoundPost();
        responseCall.enqueue(new Callback<List<FoundPostModel>>() {
            @Override
            public void onResponse(Call<List<FoundPostModel>> call, Response<List<FoundPostModel>> response) {
                foundlist=response.body();
                FoundAdapter foundAdapter = new FoundAdapter(getApplicationContext(),foundlist);
                recyclerView.setAdapter(foundAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Toast.makeText(FoundListActivity.this, "Found Items", Toast.LENGTH_SHORT).show();
//                Toast.makeText(FoundListActivity.this, "pass"+response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<FoundPostModel>> call, Throwable t) {
                Toast.makeText(FoundListActivity.this, "Fail"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        backArrow = findViewById(R.id.ivBArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoundListActivity.this, MainBoard.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
