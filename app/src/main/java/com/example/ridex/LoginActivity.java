package com.example.ridex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class LoginActivity extends AppCompatActivity {
    private final static String ACTIVITY_NAME = "LoginActivity";
    EditText emailInput, passInput;
    Button signInBtn, signInWithGoogleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the contentView
        setContentView(R.layout.activity_login);
        // Initialize the variables
        emailInput = findViewById(R.id.email_inputField);
        passInput = findViewById(R.id.password_inputField);
        signInBtn = findViewById(R.id.signIn_btn);
        signInWithGoogleBtn = findViewById(R.id.signIn_with_google_btn);
        // End of variable initialization
    }

    public void signInBtnListener(View view){
        // User clicked on the sign in btn
        String emailInputText = emailInput.getText().toString().toLowerCase().replaceAll(" ", "");
        Log.i(ACTIVITY_NAME, "Email: "+ emailInputText);
        String passwordInputText = passInput.getText().toString();
        if (!emailInputText.isEmpty() && !passwordInputText.isEmpty()) {
            // TODO: Implement processing. Progress.
            App app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
            Credentials credentials = Credentials.emailPassword(emailInputText, passwordInputText);
            // Credentials credentials = Credentials.anonymous();
            app.loginAsync(credentials, result -> {
                if (result.isSuccess()) {
                    User user = app.currentUser();

                    assert user != null;
                    Log.i(ACTIVITY_NAME, "User: " + user.getProfile());
//                    Toast.makeText(getApplicationContext(), "Successfully authenticated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid password or email address. Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Email or password cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInWithGoogleBtnListener(View view){
        // User clicked on the sign in with google btn
        Toast.makeText(getApplicationContext(), "Sign in with google is pressed!", Toast.LENGTH_SHORT).show();
    }

    public void signUpBtnTransition(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart()");
    }
}