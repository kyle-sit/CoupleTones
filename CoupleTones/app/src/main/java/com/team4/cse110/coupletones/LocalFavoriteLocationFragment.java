package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;

/* This class is for implementing our FavoriteLocationsList as a fragment */
public class LocalFavoriteLocationFragment extends ListFragment implements FavoriteLocationsList
{
    int currSelected = 0;
    private ArrayAdapter<FavoriteLocation> adapter;
    private Context context;
    private View view;
    private TextView textView_noFavLocs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_layout, container, false);
        textView_noFavLocs = (TextView) view.findViewById(R.id.textView_noFavLocs);
        textView_noFavLocs.setVisibility(View.INVISIBLE);
        return view;
    }

    /* setting the list adapter which takes in our FavoriteLocationList as displays it on our fragment */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<FavoriteLocation>(context, android.R.layout.simple_list_item_activated_1, local_favLocList);
        setListAdapter(adapter);
        updateDataSet();


        if( savedInstanceState != null)
        {
            currSelected = savedInstanceState.getInt("curChoice", 0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    /* converts our local_favLocList variable from our Interface into an array;
     * the dilemma we had was the following: we needed to constantly add
     * FavoriteLocations without restriction which means we must use a list of some sort
     */
    public FavoriteLocation[] createArray()
    {
        return local_favLocList.toArray(new FavoriteLocation[local_favLocList.size()]);
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

        //giving the user an editText box to rename the favoriteLocation
        final EditText userInput = new EditText(context);
        userInput.setHint(favLoc.getTitle());

        userInput.setInputType(InputType.TYPE_CLASS_TEXT);
        buildDialog.setView(userInput);

        //renaming the location
        buildDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String markerTitle = userInput.getText().toString();
                if (markerTitle.isEmpty())
                {
                    return;
                }

                //cannot have duplicate names
                for (FavoriteLocation favoriteLocation: local_favLocList)
                {
                    if (favoriteLocation.getTitle().equals(markerTitle))
                    {
                        Toast.makeText(context, "Unsuccessful Edit. "+markerTitle+" already exists.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(context, "Changed '" + favLoc.getTitle()+"' to '"+markerTitle+"'", Toast.LENGTH_LONG).show();
                removeLocation_firebase(favLoc);
                editLocation(favLoc, markerTitle);
                addLocation_firebase(favLoc);
            }
        });

        //cancelling option
        buildDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        // delete this favorite location
        buildDialog.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteLocation(favLoc);
            }
        });

        buildDialog.show();
    }

    private void addLocation_firebase(FavoriteLocation favLoc)
    {
        String firebaseUrl = Constants.FIREBASE_URL+SettingsFragment.getUser_name()+Constants.FAV_LOC_URL+favLoc.getTitle();
        Firebase fBase = new Firebase(firebaseUrl);
        fBase.setValue(favLoc);
    }

    private void removeLocation_firebase(FavoriteLocation favLoc)
    {
        String firebaseUrl = Constants.FIREBASE_URL+SettingsFragment.getUser_name()+Constants.FAV_LOC_URL+favLoc.getTitle();
        Firebase fBase = new Firebase(firebaseUrl);
        fBase.removeValue();
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
        if (local_favLocList.remove(favoriteLocation))
        {
            Toast.makeText(context,
                    "Successfully deleted '"+favoriteLocation.getTitle()+"'", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,
                    "Unsuccessful deletion of '"+favoriteLocation.getTitle()+"'", Toast.LENGTH_LONG).show();
        }

        //notifying our list that our data has changed
        updateDataSet();
    }

    // editing the name of our Location
    @Override
    public void editLocation(FavoriteLocation favoriteLocation, String newName)
    {
        favoriteLocation.editName(newName);

        //notifying our list that our data has changed
        updateDataSet();
    }

    public void updateDataSet()
    {
        adapter.notifyDataSetChanged();

        if (local_favLocList.isEmpty())
        {
            textView_noFavLocs.setVisibility(View.VISIBLE);
        }
    }

}
