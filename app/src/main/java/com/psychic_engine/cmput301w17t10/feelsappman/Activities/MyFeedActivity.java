package com.psychic_engine.cmput301w17t10.feelsappman.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.psychic_engine.cmput301w17t10.feelsappman.Models.ParticipantSingleton;
import com.psychic_engine.cmput301w17t10.feelsappman.R;

import java.util.ArrayList;
import java.util.List;

public class MyFeedActivity extends AppCompatActivity {
    private ParticipantSingleton instance;
    private Spinner menuSpinner;
    private ListView myFeedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed);

        instance = ParticipantSingleton.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMyFeed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Feed");

        myFeedList = (ListView) findViewById(R.id.listViewMyFeed);

        initializeSpinner();
    }

    public void initializeSpinner(){
        //initalize menu items for spinner
        List<String> menuItems = new ArrayList<String>();
        menuItems.add("My Feed");
        menuItems.add("My Profile");
        menuItems.add("Followers");
        menuItems.add("Following");
        menuItems.add("Follower Requests");

        menuSpinner = (Spinner) (findViewById(R.id.spinnerMyFeed));

        //set adapter for spinner
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, menuItems);
        menuSpinner.setAdapter(adapterSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set default spinner item to current activity
        int spinnerPosition = adapterSpinner.getPosition("My Feed");
        menuSpinner.setSelection(spinnerPosition);

        //set onclick for spinner
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch (selectedItem) {
                    case "My Profile":
                        Intent myProfileActivity = new Intent(MyFeedActivity.this, MyProfileActivity.class);
                        startActivity(myProfileActivity);
                        break;
                    case "Followers":
                        Intent followersActivity = new Intent(MyFeedActivity.this, FollowersActivity.class);
                        startActivity(followersActivity);
                        break;
                    case "Following":
                        Intent followingActivity = new Intent(MyFeedActivity.this, FollowingActivity.class);
                        startActivity(followingActivity);
                        break;
                    case "Follower Requests":
                        Intent followerRequestActivity = new Intent(MyFeedActivity.this, FollowRequestActivity.class);
                        startActivity(followerRequestActivity);
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}