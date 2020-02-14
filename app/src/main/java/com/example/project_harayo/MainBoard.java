package com.example.project_harayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import main.FoundListActivity;
import main.FoundPostActivity;
import main.ProfileActivity;

public class MainBoard extends AppCompatActivity {

    private ImageView ivProfileIcon;
    private Button foundButton,lostButton, ivMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_board);

        ivMenu = (Button)findViewById(R.id.ivMenu);
        ivProfileIcon = findViewById(R.id.ivProfile);
        foundButton = findViewById(R.id.ibFoundButton);
        lostButton = findViewById(R.id.ibLostButton);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainBoard.this,ivMenu);
                popup.getMenuInflater().inflate(R.menu.popupmenulist, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.menu_map){
                            Intent intent = new Intent(MainBoard.this, MapsActivity.class);
                            startActivity(intent);

                        }else if (id == R.id.menu_profile) {
                            Intent intent = new Intent(MainBoard.this, ProfileActivity.class);
                            startActivity(intent);

                        }
                        Toast.makeText(MainBoard.this, item.getTitle(),Toast.LENGTH_LONG).show();
                        return MainBoard.super.onOptionsItemSelected(item);
                    }
                });
                popup.show();
            }
        });


        ivProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBoard.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }


        });

        foundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBoard.this, FoundPostActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBoard.this, FoundListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}


