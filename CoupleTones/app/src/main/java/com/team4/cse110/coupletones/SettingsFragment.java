package com.team4.cse110.coupletones;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class SettingsFragment extends Fragment implements FavoriteLocationsList
{

    private static String user_name;
    private static String user_password;
    private static String partner_name;
    private static boolean loggedIn;

    private OnFragmentInteractionListener mListener;
    private Button loginButton;
    private Button partnerNameButton;
    private Button deletePartnerButton;
    private Switch soundSwitch;
    private Switch vibrationSwitch;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText partnerNameEdit;

    private Firebase firebase_addLocalFavLoc;
    private Query query_addLocalFavLoc;

    public static SettingsFragment newInstance(String param1, String param2)
    {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragview = inflater.inflate(R.layout.fragment_settings, container, false);

        loggedIn = false;

        loginButton = (Button) fragview.findViewById(R.id.button_loginUser);
        partnerNameButton = (Button) fragview.findViewById(R.id.button_updatePartner);
        deletePartnerButton = (Button) fragview.findViewById(R.id.button_forgetPartner);

        soundSwitch = (Switch) fragview.findViewById(R.id.switch_sounds);
        vibrationSwitch = (Switch) fragview.findViewById(R.id.switch_vibration);

        usernameEdit = (EditText) fragview.findViewById(R.id.editText_username);
        passwordEdit = (EditText) fragview.findViewById(R.id.editText_password);
        partnerNameEdit = (EditText) fragview.findViewById(R.id.editText_username_partner);


        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_name = usernameEdit.getText().toString();
                        user_password = passwordEdit.getText().toString();

                        checkCredentials(user_name, user_password);
                    }
                }
        );

        partnerNameButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        partner_name = partnerNameEdit.getText().toString();

                        Toast.makeText(getContext(), "Successful Partner UserName Edit", Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("partner_name",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();

                        editor.putString("partnerName",partner_name);
                        editor.apply();
                    }
                }
        );

        deletePartnerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        partner_name = "";

                        Toast.makeText(getContext(), "Successful Partner UserName Edit", Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("partner_name",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();

                        editor.putString("partnerName",partner_name);
                        editor.apply();
                    }
                }
        );
        return fragview;
    }

    private void checkCredentials(final String user_name, final String user_password)
    {
        final Firebase fBase = new Firebase(Constants.FIREBASE_URL+user_name);
        Query queryRef = fBase.orderByKey().equalTo("password");

        queryRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot == null || snapshot.getValue() == null)
                {
                    Toast.makeText(getContext(), "Creating new user...", Toast.LENGTH_SHORT).show();
                    loggedIn = true;

                    fBase.child("password").setValue(user_password);
                }
                else
                {
                    DataSnapshot temp = snapshot.getChildren().iterator().next();
                    String realPassword = temp.getValue().toString();

                    if (realPassword.equals(user_password))
                    {
                        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                        loggedIn = true;
                        loadFromFirebase(user_name);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Incorrect Password. Try Again.", Toast.LENGTH_SHORT).show();
                        loggedIn = false;
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void addLocation(FavoriteLocation favoriteLocation) {

    }

    @Override
    public void deleteLocation(FavoriteLocation favoriteLocation) {

    }

    @Override
    public void editLocation(FavoriteLocation favoriteLocation, String newName) {

    }


    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }

    public static String getUser_name()
    {

        return user_name;
    }

    public static String getPartner_name()
    {

        return partner_name;
    }

    public static boolean getLoggedIn()
    {
        return loggedIn;
    }

    //Load locations we saved
    public void loadFromFirebase(String user_name)
    {
        firebase_addLocalFavLoc = new Firebase(Constants.FIREBASE_URL + user_name + Constants.FAV_LOC_URL);
        query_addLocalFavLoc = firebase_addLocalFavLoc.orderByPriority();

        query_addLocalFavLoc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                FavoriteLocation favoriteLocation = dataSnapshot.getValue(FavoriteLocation.class);
                double latitude = dataSnapshot.child("latitude").getValue(double.class);
                double longitude = dataSnapshot.child("longitude").getValue(double.class);
                String snippet = dataSnapshot.child("snippet").getValue(String.class);
                String title = dataSnapshot.child("title").getValue(String.class);

                local_favLocList.add(new FavoriteLocation(latitude,longitude,snippet,title));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

}