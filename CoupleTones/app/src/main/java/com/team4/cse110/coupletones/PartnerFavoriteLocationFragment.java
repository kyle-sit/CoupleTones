package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


/* This class is for implementing our FavoriteLocationsList as a fragment */
public class PartnerFavoriteLocationFragment extends ListFragment implements FavoriteLocationsList
{
    int currSelected = 0;
    private ArrayAdapter<FavoriteLocation> adapter;
    private Context context;
    private View view;
    private TextView textView_noPartner;
    private TextView textView_noPartnerFavLocs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_layout, container, false);

        textView_noPartner = (TextView) view.findViewById(R.id.textView_noPartner);
        textView_noPartnerFavLocs = (TextView) view.findViewById(R.id.textView_noPartnerFavLocs);

        return view;
    }

    /* setting the list adapter which takes in our FavoriteLocationList as displays it on our fragment */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<FavoriteLocation>(context, android.R.layout.simple_list_item_activated_1, partner_favLocList);
        setListAdapter(adapter);
        updateDataSet();

        if( savedInstanceState != null)
        {
            currSelected = savedInstanceState.getInt("curChoice", 0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    /* converts our partner_favLocList variable from our Interface into an array;
     * the dilemma we had was the following: we needed to constantly add
     * FavoriteLocations without restriction which means we must use a list of some sort
     */
    public FavoriteLocation[] createArray()
    {
        return partner_favLocList.toArray(new FavoriteLocation[partner_favLocList.size()]);
    }


    /* this method is called whenever the user clicks on the FavoriteLocation list; it brings up
     * options to delete and edit the favoriteLocation */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //get the item the user clicked on
        final FavoriteLocation favLoc = (FavoriteLocation) this.getListAdapter().getItem(position);

        //creating a dialog prompting for user input
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(context);
        buildDialog.setTitle("Rename this Favorite Location");


        buildDialog.show();
    }

    @Override
    public void addLocation(FavoriteLocation favoriteLocation) {
    }

    /*
     * removing the favorite location
     */
    @Override
    public void deleteLocation(FavoriteLocation favoriteLocation)
    {
    }

    // editing the name of our Location
    @Override
    public void editLocation(FavoriteLocation favoriteLocation, String newName)
    {

    }

    public void updateDataSet()
    {
        adapter.notifyDataSetChanged();

        if (SettingsFragment.getPartner_name().equals(""))
        {
            textView_noPartner.setVisibility(View.VISIBLE);
        }
        else if (partner_favLocList.isEmpty())
        {
            textView_noPartnerFavLocs.setVisibility(View.VISIBLE);
        }
    }

}
