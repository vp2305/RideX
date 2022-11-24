package com.example.ridex;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MongoDb extends Application {
    private final static String ACTIVITY_NAME="MongoDb";
    public final static String appId = "ridex-application-endzs";
    public final static String mongoClient = "mongodb-atlas";
    public final static String databaseName = "rideX";
    public final static String usersCollection = "Users";
    public final static String realmName = "rideX.realm";
    private static final String REALM_TAG = "__REALM__";

    // Public Key: nmoluriz
    // Private Key: 6170a1e8-c362-4412-aa79-c18b38af11c7
    // Authenticate to the realm-cli: realm-cli login --api-key nmoluriz --private-api-key 6170a1e8-c362-4412-aa79-c18b38af11c7
    // Download via realm-cli: realm-cli pull --remote ridex-application-endzs --template kotlin.todo.flex
    // Follow README: RideX-Application/frontend/kotlin.todo.flex/README.md

    // This is for opening a realm
    // RealmConfiguration config = new RealmConfiguration.Builder().name(MongoDb.realmName).build();
    // Realm backgroundThreadRealm = Realm.getInstance(config);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(ACTIVITY_NAME, "OnCreate()");
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(realmName).schemaVersion(1).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

//    public static Realm getRealm(Context context) {
//        // noinspection ResourceType
//        return (Realm)context.getSystemService(REALM_TAG);
//    }
//
//    @Override
//    public Object getSystemService(@NonNull String name) {
//        if(REALM_TAG.equals(name)) {
//            return realm;
//        }
//        return super.getSystemService(name);
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(ACTIVITY_NAME, "onLowMemory()");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(ACTIVITY_NAME, "onTerminate()");
    }
}
