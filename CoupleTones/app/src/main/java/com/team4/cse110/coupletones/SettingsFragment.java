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

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;


public class SettingsFragment extends Fragment implements FavoriteLocationsList
{

    private static String user_name;
    private static String user_password;
    private static String partner_name;
    private static boolean loggedIn = false;

    private OnFragmentInteractionListener mListener;
    private Button loginButton;
    private Button partnerNameButton;
    private Button deletePartnerButton;
    private Switch soundSwitch;
    private Switch vibrationSwitch;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText partnerNameEdit;

    private Firebase firebase_addLocalFavLoc_local;
    private Query query_addLocalFavLoc_local;
    private ChildEventListener childEventListener_local;

    private Firebase firebase_addLocalFavLoc_partner;
    private Query query_addLocalFavLoc_partner;
    private ChildEventListener childEventListener_partner;
    private Comparator<FavoriteLocation> favoriteLocationComparator;

    public SettingsFragment()
    {
        childEventListener_local = new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null)
                {
                    double latitude = dataSnapshot.child("latitude").getValue(double.class);
                    double longitude = dataSnapshot.child("longitude").getValue(double.class);
                    String snippet = dataSnapshot.child("snippet").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    int priority = dataSnapshot.child("priority").getValue(int.class);

                    local_favLocList.add(new FavoriteLocation(latitude, longitude, snippet, title, priority));
                    Collections.sort(local_favLocList, favoriteLocationComparator);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s){
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        };

        childEventListener_partner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    double latitude = dataSnapshot.child("latitude").getValue(double.class);
                    double longitude = dataSnapshot.child("longitude").getValue(double.class);
                    String snippet = dataSnapshot.child("snippet").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    int priority = dataSnapshot.child("priority").getValue(int.class);

                    partner_favLocList.add(new FavoriteLocation(latitude, longitude, snippet, title, priority));
                    Collections.sort(partner_favLocList, favoriteLocationComparator);

                }
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
        };

        partner_name = "";

        favoriteLocationComparator = new Comparator<FavoriteLocation>() {
            @Override
            public int compare(FavoriteLocation lhs, FavoriteLocation rhs)
            {
                if (lhs.getPriority() > rhs.getPriority())
                {
                    return 1;
                }
                if (lhs.getPriority() < rhs.getPriority())
                {
                    return -1;
                }
                return 0;
            }
        };
    }

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
        final View fragview = inflater.inflate(R.layout.fragment_settings, container, false);

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

        partnerNameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if (loggedIn)
                        {
                            partner_name = partnerNameEdit.getText().toString();

                            final Firebase fBase = new Firebase(Constants.FIREBASE_URL);
                            Query query = fBase.orderByKey();

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    if (partner_name.equals("") || dataSnapshot == null || !(dataSnapshot.child(partner_name).exists()))
                                    {
                                        Toast.makeText(getContext(), "Invalid Partner", Toast.LENGTH_SHORT).show();
                                    } else
                                    {
                                        fBase.child(user_name).child("partner").setValue(partner_name);
                                        fBase.child(partner_name).child("partner").setValue(user_name);
                                        Toast.makeText(getContext(), "Successful Pairing with "+ partner_name, Toast.LENGTH_LONG).show();

                                        loadFromFirebase_partner(partner_name);
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Please login prior to setting up a Partner", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        deletePartnerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        if (loggedIn)
                        {
                            Firebase firebase = new Firebase(Constants.FIREBASE_URL + user_name);
                            firebase.child("partner").setValue("");

                            firebase = new Firebase(Constants.FIREBASE_URL + partner_name);
                            firebase.child("partner").setValue("");

                            partner_name = "";
                            Toast.makeText(getContext(), "Deleted Partner", Toast.LENGTH_LONG).show();

                            loadFromFirebase_partner(partner_name);

                        }

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
                        loadFromFirebase_local(user_name);
                        updatePartnerName(user_name);
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
    public void loadFromFirebase_local(String user_name)
    {
        if (user_name.equals(""))
        {
            firebase_addLocalFavLoc_local = null;
            query_addLocalFavLoc_local.removeEventListener(childEventListener_local);
            query_addLocalFavLoc_local = null;

            local_favLocList.clear();
            loggedIn = false;
        }
        else
        {
            firebase_addLocalFavLoc_local = new Firebase(Constants.FIREBASE_URL + user_name + Constants.FAV_LOC_URL);
            query_addLocalFavLoc_local = firebase_addLocalFavLoc_local.orderByPriority();

            query_addLocalFavLoc_local.addChildEventListener(childEventListener_local);
        }

    }

    //Load locations we saved
    public void loadFromFirebase_partner(String partner_name)
    {
        if (partner_name.equals(""))
        {
            firebase_addLocalFavLoc_partner = null;
            query_addLocalFavLoc_local.removeEventListener(childEventListener_partner);
            query_addLocalFavLoc_partner = null;

            partner_favLocList.clear();

            partnerNameEdit.setText("");
        }
        else
        {
            firebase_addLocalFavLoc_partner = new Firebase(Constants.FIREBASE_URL + partner_name + Constants.FAV_LOC_URL);
            query_addLocalFavLoc_partner = firebase_addLocalFavLoc_partner.orderByPriority();
            query_addLocalFavLoc_partner.addChildEventListener(childEventListener_partner);

            partnerNameEdit.setText(partner_name);
        }

    }

    public void updatePartnerName(String user_name)
    {
        Firebase firebase = new Firebase(Constants.FIREBASE_URL+user_name);
        Query query = firebase.orderByKey().equalTo("partner");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.child("partner").exists())
                {
                    partner_name = dataSnapshot.child("partner").getValue().toString();
                    loadFromFirebase_partner(partner_name);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

}