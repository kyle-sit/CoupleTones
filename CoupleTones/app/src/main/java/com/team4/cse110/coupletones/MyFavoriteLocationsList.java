package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;git
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyFavoriteLocationsList extends Fragment {

    private List<FavoriteLocation> list;

    public MyFavoriteLocationsList() {

        list = new ArrayList<FavoriteLocation>();
    }

    public void addLocation(FavoriteLocation favoriteLocation) {
        list.add(0, favoriteLocation);
    }

    private void deleteLocation(FavoriteLocation favoriteLocation)
    {
        list.remove(favoriteLocation);
    }

    private void editLocation(FavoriteLocation favoriteLocation)
    {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
        buildDialog.setTitle("Name your Favorite Location");
        final EditText userInput = new EditText(this);
    }

    private FavoriteLocation[] createArray()
    {
        return list.toArray(new FavoriteLocation[list.size()]);
    }
    private static final int CONTENT_VIEW_ID = 10101010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_locations_list);


        ListAdapter adapter = new ArrayAdapter<FavoriteLocation>(this, android.R.layout.simple_list_item_1,
                createArray());

        ListView theListView = (ListView) findViewById(R.id.myFavoriteLocationsList);
        theListView.setAdapter(adapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState){

    }


}
