package com.example.ridex;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MongoDb extends Application {
    private final static String ACTIVITY_NAME="MongoDb";
    public final static String appId = "ridex-application-endzs";
    public final static String mongoClient = "mongodb-atlas";
    public final static String databaseName = "rideX";
    public final static String usersCollection = "Users";

    // Public Key: nmoluriz
    // Private Key: 6170a1e8-c362-4412-aa79-c18b38af11c7
    // Authenticate to the realm-cli: realm-cli login --api-key nmoluriz --private-api-key 6170a1e8-c362-4412-aa79-c18b38af11c7
    // Download via realm-cli: realm-cli pull --remote ridex-application-endzs --template kotlin.todo.flex
    // Follow README: RideX-Application/frontend/kotlin.todo.flex/README.md

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(ACTIVITY_NAME, "OnCreate()");
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("rideX.realm").schemaVersion(0).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
