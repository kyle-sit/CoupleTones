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


public class SettingsFragment extends Fragment
{

    private static String user_name;
    private static String user_password;
    private static String partner_name;
    private static boolean loggedIn;

    private OnFragmentInteractionListener mListener;

    public static SettingsFragment newInstance(String param1, String param2) {
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

        Button loginButton = (Button) fragview.findViewById(R.id.button_loginUser);
        Button partnerNameButton = (Button) fragview.findViewById(R.id.button_updatePartner);
        Button deletePartnerButton = (Button) fragview.findViewById(R.id.button_forgetPartner);

        Switch soundSwitch = (Switch) fragview.findViewById(R.id.switch_sounds);
        Switch vibrationSwitch = (Switch) fragview.findViewById(R.id.switch_vibration);

        final EditText usernameEdit = (EditText) fragview.findViewById(R.id.editText_username);
        final EditText passwordEdit = (EditText) fragview.findViewById(R.id.editText_password);
        final EditText partnerNameEdit = (EditText) fragview.findViewById(R.id.editText_username_partner);

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_name = usernameEdit.getText().toString();
                        user_password = passwordEdit.getText().toString();

                        //Toast.makeText(getContext(), "Successful Partner UserName Edit", Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_name",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("userName",user_name);
                        editor.putString("userPassword", user_password);
                        editor.apply();
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
}