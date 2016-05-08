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
import android.widget.Toast;

import com.google.android.gms.location.Geofence;


public class FavoriteLocationFragment extends ListFragment implements FavoriteLocationsList
{
    int currSelected = 0;
    private ArrayAdapter<FavoriteLocation> adapter;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<FavoriteLocation>(getActivity(), android.R.layout.simple_list_item_activated_1, favLocList);
        setListAdapter(adapter);

        if( savedInstanceState != null)
        {
            currSelected = savedInstanceState.getInt("curChoice", 0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public FavoriteLocation[] createArray()
    {
        return favLocList.toArray(new FavoriteLocation[favLocList.size()]);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final FavoriteLocation favLoc = (FavoriteLocation) this.getListAdapter().getItem(position);

        AlertDialog.Builder buildDialog = new AlertDialog.Builder(context);
        buildDialog.setTitle("Rename this Favorite Location");
        final EditText userInput = new EditText(context);
        userInput.setHint(favLoc.getTitle());

        userInput.setInputType(InputType.TYPE_CLASS_TEXT);
        buildDialog.setView(userInput);

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

                for (FavoriteLocation favoriteLocation: favLocList)
                {
                    if (favoriteLocation.getTitle().equals(markerTitle))
                    {
                        Toast.makeText(context, "Unsuccessful Edit. "+markerTitle+" already exists.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(context, "Changed '" + favLoc.getTitle()+"' to '"+markerTitle+"'", Toast.LENGTH_LONG).show();
                editLocation(favLoc, markerTitle);
            }
        });

        buildDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        buildDialog.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteLocation(favLoc);
            }
        });

        buildDialog.show();
    }

    @Override
    public void addLocation(FavoriteLocation favoriteLocation) {
    }

    @Override
    public void deleteLocation(FavoriteLocation favoriteLocation)
    {
        if (favLocList.remove(favoriteLocation))
        {
            Toast.makeText(context,
                    "Successfully deleted '"+favoriteLocation.getTitle()+"'", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,
                    "Unsuccessful deletion of '"+favoriteLocation.getTitle()+"'", Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void editLocation(FavoriteLocation favoriteLocation, String newName)
    {
        favoriteLocation.editName(newName);
        adapter.notifyDataSetChanged();
    }

}
