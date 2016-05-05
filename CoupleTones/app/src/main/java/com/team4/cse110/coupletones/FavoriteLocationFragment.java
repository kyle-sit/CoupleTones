package com.team4.cse110.coupletones;

import android.support.v4.app.ListFragment;
import android.view.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class FavoriteLocationFragment extends ListFragment implements FavoriteLocationsList
{
    int currSelected = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<FavoriteLocation> adapter = new ArrayAdapter<FavoriteLocation>(getActivity(),
                android.R.layout.simple_list_item_activated_1, list);
        setListAdapter(adapter);

        if( savedInstanceState != null)
        {
            currSelected = savedInstanceState.getInt("curChoice", 0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public FavoriteLocation[] createArray()
    {
        return list.toArray(new FavoriteLocation[list.size()]);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public void addLocation(FavoriteLocation favoriteLocation) {
    }

    @Override
    public void deleteLocation(FavoriteLocation favoriteLocation)
    {

    }

    @Override
    public void editLocation(FavoriteLocation favoriteLocation)
    {

    }
}
