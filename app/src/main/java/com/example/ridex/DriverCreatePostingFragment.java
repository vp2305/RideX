package com.example.ridex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ridex.models.Posts;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverCreatePostingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverCreatePostingFragment extends Fragment {
    private static final String ACTIVITY_NAME = "DriverCreatePostingFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // UI Variables
    EditText fromLocation,
            toLocation,
            date,
            time,
            carModel,
            carYear,
            licensePlate,
            carColor,
            postDescription;
    Spinner seatsAvailable;
    Button driverPostBtn;

    // MongoDB Variables
    App app;
    Realm realm;

    public DriverCreatePostingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverCreatePostingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverCreatePostingFragment newInstance(String param1, String param2) {
        DriverCreatePostingFragment fragment = new DriverCreatePostingFragment();
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

        app = new App(new AppConfiguration.Builder(MongoDb.appId).build());
        realm = MainActivity.getRealm(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_create_posting,
                container, false);

        // Assign UI elements
        fromLocation = view.findViewById(R.id.driverFromLocation);
        toLocation = view.findViewById(R.id.driverToLocation);
        date = view.findViewById(R.id.driverRideDate);
        time = view.findViewById(R.id.driverRideTime);
        seatsAvailable = view.findViewById(R.id.availableSeats);
        postDescription = view.findViewById(R.id.postDescription);
        carModel = view.findViewById(R.id.carModel);
        carYear = view.findViewById(R.id.carYear);
        carColor = view.findViewById(R.id.carColor);
        licensePlate = view.findViewById(R.id.carLicensePlate);
        driverPostBtn = view.findViewById(R.id.driverPostBtn);
        // End of assigning UI elements

        driverPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromLocation.getText().toString().isEmpty() ||
                        toLocation.getText().toString().isEmpty() ||
                        date.getText().toString().isEmpty() ||
                        time.getText().toString().isEmpty() ||
                        carModel.getText().toString().isEmpty() ||
                        carYear.getText().toString().isEmpty() ||
                        licensePlate.getText().toString().isEmpty() ||
                        carColor.getText().toString().isEmpty()
                ) {
                    // Please fill in all the details before creating all the information.
                    Toast.makeText(requireContext(),
                            "Please fill in all the details before creating all the information.",
                            Toast.LENGTH_LONG).show();
                } else {
                    realm.executeTransactionAsync(r -> {
                        RealmList<String> confirmedUsersList = new RealmList<>();
                        confirmedUsersList.add("");
                        Posts driverPost = r.createObject(
                                Posts.class,
                                new ObjectId()
                        );
                        driverPost.setPostedAs("Driver");
                        driverPost.setPosterUID(app.currentUser().getId());
                        driverPost.setFromLocation(fromLocation.getText().toString());
                        driverPost.setToLocation(toLocation.getText().toString());
                        driverPost.setDate(date.getText().toString());
                        driverPost.setTime(time.getText().toString());
                        driverPost.setNumberOfSeats(
                                Integer.parseInt(seatsAvailable.getSelectedItem().toString()));
                        driverPost.setAvailableSeats(
                                Integer.parseInt(seatsAvailable.getSelectedItem().toString()));
                        driverPost.setPostDescription(postDescription.getText().toString());
                        driverPost.setConfirmedUsers(confirmedUsersList);
                        driverPost.setCarModel(carModel.getText().toString());
                        driverPost.setCarColor(carColor.getText().toString());
                        driverPost.setCarYear(carYear.getText().toString());
                        driverPost.setLicensePlate(licensePlate.getText().toString());

                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getContext(),
                                    "Inserted the post into the db!", Toast.LENGTH_LONG).show();

                            fromLocation.setText("");
                            toLocation.setText("");
                            date.setText("");
                            time.setText("");
                            carModel.setText("");
                            carYear.setText("");
                            licensePlate.setText("");
                            seatsAvailable.setSelection(0);
                            carColor.setText("");
                            postDescription.setText("");
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.i(ACTIVITY_NAME, "Error inserting: " + error.toString());
                        }
                    });
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}