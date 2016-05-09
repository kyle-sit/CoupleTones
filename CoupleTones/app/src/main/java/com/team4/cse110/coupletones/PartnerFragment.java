package com.team4.cse110.coupletones;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.drive.internal.StringListResponse;
import com.google.android.gms.nearby.messages.Strategy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartnerFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private static String partner_name;
    private static String partner_number;

    private OnFragmentInteractionListener mListener;

    public PartnerFragment(){
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("partner_name",0);
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("partner_number",0);
        String name = sharedPreferences.getString("partnerName","");
        String number = sharedPreferences1.getString("partnerNumber","");
        setName(name);
        setNumber(number);
        System.out.println(name);
        System.out.println(number);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragview = inflater.inflate(R.layout.fragment_partner, container, false);
        Button nameBtn = (Button) fragview.findViewById(R.id.nameButton);
        Button numBtn = (Button) fragview.findViewById(R.id.numberButton);

        final EditText nameEdit = (EditText) fragview.findViewById(R.id.partnerName);
        final EditText numberEdit = (EditText) fragview.findViewById(R.id.partnerNumber);



        nameBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        partner_name = nameEdit.getText().toString();
                        System.out.println(partner_name);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("partner_name",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("partnerName",partner_name);
                        System.out.println(partner_name);
                        editor.apply();
                    }
                }
        );

        numBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        partner_number = numberEdit.getText().toString();
                        System.out.println(partner_number);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("partner_number",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("partnerNumber",partner_number);
                        System.out.println(partner_number);
                        editor.apply();
                        sendSms();
                    }
                }
        );

        // Inflate the layout for this fragment
        return fragview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void sendSms() {
        String number = partner_number;
        String userName = "";
        String message;

        if(UserInfoFragment.getName() != null) {
            userName = UserInfoFragment.getName();
            message = userName + " has added you as a partner on CoupleTones!";
        }
        else{
            message = "This number added you as a partner on CoupleTones!";
        }


        SmsManager manager = SmsManager.getDefault();

        manager.sendTextMessage(number, null, message, null, null);
        //Toast.makeText(getApplicationContext(), "send successfully", Toast.LENGTH_LONG).show();
    }

    protected static String getPartner_name()
    {
        return partner_name;
    }

    protected static String getPartner_number()
    {
        return partner_number;
    }

    protected void setName(String name){
        this.partner_name = name;
    }

    protected void setNumber(String number){
        this.partner_number = number;
    }
}
