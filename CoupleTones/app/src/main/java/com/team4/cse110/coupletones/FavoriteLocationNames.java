package com.team4.cse110.coupletones;

import android.support.v4.app.ListFragment;
import android.view.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kyle on 5/4/16.
 */
public class FavoriteLocationNames extends ListFragment {
    int currSelected = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //FavoriteLocationsList array = new FavoriteLocationsList();

        //ArrayAdapter<FavoriteLocation> connectArrayToList =
                //new ArrayAdapter<FavoriteLocation>(getActivity(),
                        //android.R.layout.simple_list_item_activated_1,
                        //array.createArray());

        String [] fake = {"aren", "kyle", "niral", "Aram", "loves", "beirut", "I ", "love", "cock", "isnt", "it", "great", "YEET", "hellll yeah", "who,", "me?"};

        ArrayAdapter<String> connectArrayToList =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        fake);

        setListAdapter(connectArrayToList);

        if( savedInstanceState != null) {
            currSelected = savedInstanceState.getInt("curChoice", 0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }
}
