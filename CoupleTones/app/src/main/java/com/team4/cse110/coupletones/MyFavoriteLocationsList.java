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
import android.view.LayoutInflater;
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

public class MyFavoriteLocationsList extends AppCompatActivity {

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
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        if (savedInstanceState == null) {
            Fragment newFragment = new MapActivity.MapFragment();
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(CONTENT_VIEW_ID, newFragment).commit();
        }

        //ListAdapter adapter = new ArrayAdapter<FavoriteLocation>(this, android.R.layout.simple_list_item_1,
             //   createArray());

        String [] str = {"aren", "kyle"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                str);

        ListView theListView = (ListView) findViewById(R.id.myFavoriteLocationsList);
        theListView.setAdapter(adapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.your_placeholder, new android.support.v4.app.ListFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

    }

    public static class ListFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
            // Defines the xml file for the fragment
            return inflater.inflate(R.layout.content_my_favorite_locations_list, parent, false);
        }

    }
}
