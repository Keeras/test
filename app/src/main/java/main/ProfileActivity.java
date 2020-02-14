package main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_harayo.MainBoard;
import com.example.project_harayo.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText userMobileNo, userEmail, userAddress;
    private TextView userName;
    private ImageView BackPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.etUserFName);
        userMobileNo = findViewById(R.id.etUserPhoneNo);
        userEmail = findViewById(R.id.etUserEmail);
        userAddress = findViewById(R.id.etUserAddress);
        BackPro = findViewById(R.id.ivBackPro);
        BackPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
