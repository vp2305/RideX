package com.example.ridex;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridex.models.user;

import java.util.Objects;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;

public class HomePageActivity extends AppCompatActivity {
    private final static String ACTIVITY_NAME = "HomePageActivity";
    TextView greetingText;
    user currUserInfo;
    ImageButton profileBtn;

    RealmObjectChangeListener<user> userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        App app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
        SyncConfiguration configuration =
                new SyncConfiguration.Builder(Objects.requireNonNull(app.currentUser()))
                .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                    @Override
                    public void configure(@NonNull Realm realm,
                                          @NonNull MutableSubscriptionSet subscriptions) {
                        // Add a subscription with a name
                        subscriptions.addOrUpdate(Subscription.create("userQuery",
                                realm.where(user.class)
                                        .equalTo("uid", app.currentUser().getId())
                        ));
                    }
                })
                .build();

        Realm realm = Realm.getInstance(configuration);

//        userListener = (changedUser, changeSet) -> {
//            if (changeSet.isDeleted()){
//                Log.i(ACTIVITY_NAME, "User is deleted!");
//                Toast.makeText(getApplicationContext(),
//                        "There is seems to be some problem with your account!",
//                        Toast.LENGTH_SHORT).show();
//                app.currentUser().logOutAsync(result -> {
//                    if (result.isSuccess()){
//                        Intent intent = new Intent(HomePageActivity.this,
//                                LoginActivity.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            for (String fieldName : changeSet.getChangedFields()){
//                Log.i(ACTIVITY_NAME, "Field '" + fieldName + "' changed.");
//                switch (fieldName) {
//                    case "lastName":
//                    case "firstName":
//                        greetingText.setText(String.format("Hello, %s %s",
//                                currUserInfo.getFirstName(), currUserInfo.getLastName()));
//                        break;
//                    case "email":
//                        Log.i(ACTIVITY_NAME, "Email is changed!");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };

//        currUserInfo = realm.where(user.class)
//                .equalTo("uid", app.currentUser().getId()).findFirst();

//        currUserInfo.addChangeListener(userListener);

        // Get all the field
        greetingText = findViewById(R.id.greeting_text);
        profileBtn = findViewById(R.id.home_page_profile_btn);
        // End of all the field collection

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,
                        AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart()");
//        greetingText.setText(String.format("Hello, %s %s",
//                currUserInfo.getFirstName(), currUserInfo.getLastName()));
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
        // Clear out the listener
        currUserInfo.removeChangeListener(userListener);
    }
}