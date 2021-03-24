package com.example.tiangge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    Button callSignUp, getLog_btn;
    TextInputLayout getUsername, getPassword;



    protected static final String EXTRA_MESSAGE = "juantribe.android.proj.com.tiangge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        callSignUp = findViewById(R.id.signup_btn);
        getLog_btn = findViewById(R.id.login_btn);
        getUsername = findViewById(R.id.username);
        getPassword = findViewById(R.id.password);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        getLog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEnteredUsername = getUsername.getEditText().getText().toString().trim();
                final String userEnteredPassword = getPassword.getEditText().getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            getUsername.setError(null);
                            getUsername.setErrorEnabled(false);
                            String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                            if (passwordFromDB.equals(userEnteredPassword)) {
                                getUsername.setError(null);
                                getPassword.setErrorEnabled(false);
                                String userName = getUsername.getEditText().getText().toString();
                                Intent intent = new Intent(LogInActivity.this, SuccessLogin.class);
                                intent.putExtra(EXTRA_MESSAGE, "Hi " + userName + "!");
                                startActivity(intent);
                            }
                            else {
                                getPassword.setError("Wrong Password");
                                getPassword.requestFocus();
                            }
                        }
                        else {
                            getUsername.setError("No such User exist");
                            getUsername.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private Boolean validateUsername(){
        String val = getUsername.getEditText().getText().toString();
        if (val.isEmpty()){
            getUsername.setError("Field cannot be empty");
            return false;
        }
        else {
            getUsername.setError(null);
            getUsername.setEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val = getPassword.getEditText().getText().toString();

        if (val.isEmpty()){
            getPassword.setError("Field cannot be empty");
            return false;
        }
        else {
            getPassword.setError(null);
            getPassword.setErrorEnabled(false);
            return true;
        }
    }



}