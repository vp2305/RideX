package com.example.ridex;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomePageActivity extends AppCompatActivity {
    private final static String ACTIVITY_NAME = "HomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        SyncConfiguration configuration = new SyncConfiguration.Builder(user)
//                .initialSubscriptions(((realm, subscriptions) ->
//                        subscriptions.addOrUpdate(Subscription.create(
//                                "users query",
//                                realm.where(users.class).equalTo("uid", "63746911e7ebc498f7743615")
//                        ))
//                )).build();
//
//        Realm realm = Realm.getInstance(configuration);

//        Log.i(ACTIVITY_NAME, users.class.toString());
//
//        Realm realm = Realm.getInstance(configuration);

    }
}