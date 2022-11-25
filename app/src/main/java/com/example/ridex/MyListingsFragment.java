package com.example.ridex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridex.models.Posts;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MyListingsFragment extends Fragment {
    public static final String ACTIVITY_NAME = "MyListingsFragment";
    // UI
    RecyclerView myListingsRecyclerView;
    // MongoDB
    App app;
    Realm realm;
    RealmResults<Posts> myPostings;

    public MyListingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
        realm = MainActivity.getRealm(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_listings, container, false);

        myListingsRecyclerView = view.findViewById(R.id.myListingsRecyclerView);

        myPostings = realm.where(Posts.class)
                .equalTo("posterUID",
                        app.currentUser().getId())
                .sort("date", Sort.ASCENDING)
                .findAll();

        if (myPostings.size() == 0){
            emptyPostingViews();
        } else {
            displayPostingViews(myPostings);
        }

        // Inflate the layout for this fragment
        return view;
    }

    public void displayPostingViews(RealmResults<Posts> posts){
        PostingsRealmAdapter myPostings = new PostingsRealmAdapter(getContext(), posts);
        myListingsRecyclerView.setAdapter(myPostings);
        myListingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myPostings.notifyDataSetChanged();
    }
    public void emptyPostingViews(){
        EmptyPostingAdapter emptyPostingAdapter = new EmptyPostingAdapter(getContext(),
                "No postings for the search!");
        myListingsRecyclerView.setAdapter(emptyPostingAdapter);
        myListingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}