package com.example.ridex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ridex.models.Posts;

import org.bson.types.ObjectId;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Realm realm;
    Posts currentPosting;
    String postId = "";
    TextView driverInfo, driverName, driverRating, pickupLocation, dropoffLocation, rideDate, ridePrice, seatsAvailable, carModel, carLicensePlate, carColor, rideAddComments;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostDetailsFragment newInstance(String param1, String param2) {
        PostDetailsFragment fragment = new PostDetailsFragment();
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
        realm = MainActivity.getRealm(getActivity());
        if (getArguments() != null && !getArguments().getString("postId").isEmpty()){
            postId = getArguments().getString("postId");
        }
        currentPosting = realm.where(Posts.class).equalTo("_id", new ObjectId(postId)).findFirst();
        if (currentPosting.getPostedAs().equals("Driver")){
            //driverName.setText(currentPosting.get);
            //driverRating
            pickupLocation.setText(currentPosting.getFromLocation());
            dropoffLocation.setText(currentPosting.getToLocation());
            rideDate.setText(currentPosting.getDate());
            ridePrice.setText(currentPosting.getPrice().toString());
            seatsAvailable.setText(currentPosting.getAvailableSeats());
            carModel.setText(currentPosting.getCarModel());
            carLicensePlate.setText(currentPosting.getLicensePlate());
            carColor.setText(currentPosting.getCarColor());
            rideAddComments.setText(currentPosting.getPostDescription());
        }
        else{
            driverInfo.setText("Rider Information");


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        driverInfo = view.findViewById(R.id.driver_info);
        driverName = view.findViewById(R.id.driver_name);
        driverRating = view.findViewById(R.id.driver_rating);
        pickupLocation = view.findViewById(R.id.pickup_location);
        dropoffLocation = view.findViewById(R.id.dropoff_location);
        rideDate = view.findViewById(R.id.ride_date);
        ridePrice = view.findViewById(R.id.ride_price);
        seatsAvailable = view.findViewById(R.id.seats_available);
        carModel = view.findViewById(R.id.car_model);
        carLicensePlate = view.findViewById(R.id.car_license_plate);
        carColor = view.findViewById(R.id.car_color);
        rideAddComments = view.findViewById(R.id.ride_add_comments);

        return view;

    }
}