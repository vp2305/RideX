package com.example.ridex;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridex.models.user;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmObjectChangeListener;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static String ACTIVITY_NAME = "HomePageFragment";
    TextView greetingText;
    user currUserInfo;
    ImageButton profileBtn;
    RealmObjectChangeListener<user> userListener;
    App app;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//
//        app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
//        SyncConfiguration configuration =
//                new SyncConfiguration.Builder(Objects.requireNonNull(app.currentUser()))
//                        .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
//                            @Override
//                            public void configure(@NonNull Realm realm,
//                                                  @NonNull MutableSubscriptionSet subscriptions) {
//                                // Add a subscription with a name
//                                subscriptions.addOrUpdate(Subscription.create("userQuery",
//                                        realm.where(user.class)
//                                                .equalTo("uid", app.currentUser().getId())
//                                ));
//                            }
//                        })
//                        .build();

        //Realm realm = Realm.getInstance(configuration);

//        currUserInfo = realm.where(user.class)
//                .equalTo("uid", app.currentUser().getId()).findFirst();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Get all the field
        greetingText = view.findViewById(R.id.greeting_text);
        profileBtn = view.findViewById(R.id.home_page_profile_btn);
        // End of all the field collection

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        AccountActivity.class);
                startActivity(intent);
            }
        });

//        userListener = (changedUser, changeSet) -> {
//            if (changeSet.isDeleted()){
//                Log.i(ACTIVITY_NAME, "User is deleted!");
//                Toast.makeText(view.getContext(),
//                        "There is seems to be some problem with your account!",
//                        Toast.LENGTH_SHORT).show();
//                app.currentUser().logOutAsync(result -> {
//                    if (result.isSuccess()){
//                        Intent intent = new Intent(getActivity(),
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
//        currUserInfo.addChangeListener(userListener);
//
//        // Inflate the layout for this fragment
        return view;
    }
}