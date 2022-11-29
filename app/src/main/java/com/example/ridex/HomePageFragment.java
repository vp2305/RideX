package com.example.ridex;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavType;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridex.models.ChatRoom;
import com.example.ridex.models.Posts;
import com.example.ridex.models.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmResults;
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
    Button searchBtn;
    BottomNavigationView menu;
    EditText fromEditText, toEditText;
    LinearLayout activeListingLayout;
    RecyclerView activeListingLayoutRecyclerView;

    //Data
    String fromLocation, toLocation;
    Bundle bundle;

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
        realm = MainActivity.getRealm(getActivity());
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
        searchBtn = view.findViewById(R.id.search_btn);
        menu = getActivity().findViewById(R.id.bottomNavigation);
        fromEditText = view.findViewById(R.id.from_edit_text);
        toEditText = view.findViewById(R.id.to_edit_text);
        activeListingLayout = view.findViewById(R.id.confirmedListingsLayout);
        activeListingLayoutRecyclerView = view.findViewById(R.id.confirmedListingRecyclerView);
        // End of all the field collection

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromLocation = fromEditText.getText().toString();
                toLocation = toEditText.getText().toString();
                menu.findViewById(R.id.search).performClick(); //click search item change menu item
                //create new bundle to pass both locations
                bundle = new Bundle();
                bundle.putString("fromLocation", fromLocation);
                bundle.putString("toLocation", toLocation);
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, searchFragment).commit();

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findViewById(R.id.account).performClick();
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
                                currUsersInfo.getFirstName().replace(" ", ""),
                                currUsersInfo.getLastName().replace(" ", "")));
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
                currUsersInfo.getFirstName().replace(" ", ""),
                currUsersInfo.getLastName().replace(" ", "")));

//        displayConfirmedListings();

        // Inflate the layout for this fragment
        return view;
    }

    public void displayConfirmedListings(){
        if (!currUsersInfo.getConfirmedRide().get(0).equals("")){
            activeListingLayout.setVisibility(View.VISIBLE);
            confirmedListings();
        }
    }

    public void confirmedListings(){
        RealmList<Posts> confirmedPosts = new RealmList<>();

        for (int i = 0; i < currUsersInfo.getConfirmedRide().size(); i++){
            String confirmedPostIdString = currUsersInfo.getConfirmedRide().get(i);
            Posts post_confirmed = realm.where(Posts.class)
                    .equalTo("_id", new ObjectId(confirmedPostIdString)).findFirst();
            confirmedPosts.add(i, post_confirmed);
        }

        ConfirmedRideRealmAdapter confirmedRideRealmAdapter =
                new ConfirmedRideRealmAdapter(getContext(), realm, confirmedPosts);
        activeListingLayoutRecyclerView.setAdapter(confirmedRideRealmAdapter);
        activeListingLayoutRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        confirmedRideRealmAdapter.notifyDataSetChanged();
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