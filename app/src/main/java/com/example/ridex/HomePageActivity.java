package com.example.ridex;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ridex.models.user;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        App app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
        SyncConfiguration configuration = new SyncConfiguration.Builder(app.currentUser())
                .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                    @Override
                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                        // Add a subscription with a name
                        subscriptions.addOrUpdate(Subscription.create("userQuery",
                                realm.where(user.class)
                                        .equalTo("uid", app.currentUser().getId())
                        ));
                    }
                })
                .build();

        Realm realm = Realm.getInstance(configuration);

        currUserInfo = realm.where(user.class)
                .equalTo("uid", app.currentUser().getId()).findFirst();
        
        currUserInfo.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                greetingText.setText("Hello, "
                + currUserInfo.getFirstName()
                + " " + currUserInfo.getLastName());
            }
        });


        // Get all the field
        greetingText = findViewById(R.id.greeting_text);

        // End of all the field collection

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart()");
        greetingText.setText("Hello, "
                + currUserInfo.getFirstName()
                + " " + currUserInfo.getLastName());
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
}