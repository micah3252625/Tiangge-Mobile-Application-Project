package com.example.tiangge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class SuccessLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        String passedUsername = intent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.welcome_username);
        textView.setText(passedUsername);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SuccessLogin.this, HomeActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}