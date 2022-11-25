package com.example.ridex;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatWindowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatWindowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ACTIVITY_NAME = "ChatWindowFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //UI
    BottomNavigationView menu;

    ImageButton backBtn;
    FrameLayout frameLayout;
    ViewGroup.MarginLayoutParams mlp;

    public ChatWindowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatWindowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatWindowFragment newInstance(String param1, String param2) {
        ChatWindowFragment fragment = new ChatWindowFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_window, container, false);

        //get fields
        menu = getActivity().findViewById(R.id.bottomNavigation);
        backBtn = view.findViewById(R.id.backImage);
        frameLayout = getActivity().findViewById(R.id.frameLayout);

        mlp = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
        mlp.bottomMargin = 0;


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, new AccountPageFragment()).commit();
            }
        });






        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(ACTIVITY_NAME, "destryvojefsljhfbgse");
        mlp.bottomMargin = 180;

    }
}