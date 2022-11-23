package com.example.ridex;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridex.models.Users;

import io.realm.Realm;
import io.realm.RealmObjectChangeListener;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
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

    // Ui variables
    TextView greetingText;
    ImageButton profileBtn;

    // Realm variables.
    Users currUsersInfo;
    Realm realm;
    SyncConfiguration configuration;
    RealmObjectChangeListener<Users> userListener;
    App app;
//    SyncConfiguration configuration;

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
        Log.i(ACTIVITY_NAME, "onCreate()");

        app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
//        configuration =
//                new SyncConfiguration.Builder(app.currentUser())
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
        realm = MainActivity.getRealm(getActivity());
//        realm = Realm.getInstance(configuration);
        currUsersInfo = realm.where(Users.class)
                .equalTo("uid", app.currentUser().getId()).findFirst();
        Log.i(ACTIVITY_NAME, String.valueOf(currUsersInfo));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "onCreateView()");

        View view  = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Get all the field
        greetingText = view.findViewById(R.id.greeting_text);
        profileBtn = view.findViewById(R.id.home_page_profile_btn);
        // End of all the field collection

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new AccountPageFragment());
                fragmentTransaction.commit();
            }
        });

        userListener = (changedUser, changeSet) -> {
            if (changeSet.isDeleted()){
                Log.i(ACTIVITY_NAME, "User is deleted!");
                Toast.makeText(view.getContext(),
                        "There is seems to be some problem with your account!",
                        Toast.LENGTH_SHORT).show();
                app.currentUser().logOutAsync(result -> {
                    if (result.isSuccess()){
                        Intent intent = new Intent(getActivity(),
                                LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }

            for (String fieldName : changeSet.getChangedFields()){
                Log.i(ACTIVITY_NAME, "Field '" + fieldName + "' changed.");
                switch (fieldName) {
                    case "lastName":
                    case "firstName":
                        greetingText.setText(String.format("Hello, %s %s",
                                currUsersInfo.getFirstName(), currUsersInfo.getLastName()));
                        break;
                    case "email":
                        Log.i(ACTIVITY_NAME, "Email is changed!");
                        break;
                    default:
                        break;
                }
            }
        };
        currUsersInfo.addChangeListener(userListener);
        greetingText.setText(String.format("Hello, %s %s",
                currUsersInfo.getFirstName(), currUsersInfo.getLastName()));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(ACTIVITY_NAME, "onDestroyView()");
        currUsersInfo.removeChangeListener(userListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(ACTIVITY_NAME, "onDetach()");
    }
}