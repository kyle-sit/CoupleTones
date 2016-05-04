package com.team4.cse110.coupletones;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kyle on 5/4/16.
 */
public class FavoriteLocationNames extends ListFragment{
    int currSelected = 0;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FavoriteLocationsList array = new FavoriteLocationsList();

        ArrayAdapter<FavoriteLocation> connectArrayToList =
                new ArrayAdapter<FavoriteLocation>(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        array.createArray());

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
