package com.example.ridex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

import com.example.ridex.models.user;

public class AccountActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "AccountActivity";
    ImageButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize the variables
        logoutBtn = findViewById(R.id.logoutBtn);
        // End of the initialization of the variable

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
                User user = app.currentUser();
                assert user != null;
                user.logOutAsync(result -> {
                    if (result.isSuccess()){
                        Toast.makeText(getApplicationContext(),
                                "Logged out successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccountActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Unable to logout successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}