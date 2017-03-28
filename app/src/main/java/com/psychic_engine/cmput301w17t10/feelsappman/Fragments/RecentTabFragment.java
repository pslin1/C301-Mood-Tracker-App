package com.psychic_engine.cmput301w17t10.feelsappman.Fragments;

/**
 * Created by jordi on 2017-03-09.
 */
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.psychic_engine.cmput301w17t10.feelsappman.Activities.EditMoodActivity;
import com.psychic_engine.cmput301w17t10.feelsappman.Activities.ViewMoodEventActivity;
import com.psychic_engine.cmput301w17t10.feelsappman.Controllers.DeleteMoodController;
import com.psychic_engine.cmput301w17t10.feelsappman.Controllers.ElasticMoodController;
import com.psychic_engine.cmput301w17t10.feelsappman.Models.MoodEvent;
import com.psychic_engine.cmput301w17t10.feelsappman.Models.Participant;
import com.psychic_engine.cmput301w17t10.feelsappman.Models.ParticipantSingleton;
import com.psychic_engine.cmput301w17t10.feelsappman.R;

import static android.graphics.Color.parseColor;


public class RecentTabFragment extends Fragment {

    private TextView date;
    private TextView viewmood;
    private TextView location;
    private ImageView imageView;
    private Button delete;
    private Button edit;
    private Participant participant;
    private MoodEvent moodEvent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recent, container, false);
        delete = (Button)rootView.findViewById(R.id.deletebutton);
        edit = (Button)rootView.findViewById(R.id.editbutton);
        date = (TextView) rootView.findViewById(R.id.date);
        viewmood = (TextView) rootView.findViewById(R.id.mood);
        location = (TextView) rootView.findViewById(R.id.location);
        imageView = (ImageView) rootView.findViewById(R.id.picture);
        participant = ParticipantSingleton.getInstance().getSelfParticipant();
        moodEvent = participant.getMostRecentMoodEvent();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moodEvent != null) {
                    DeleteMoodController.remove(moodEvent);
                    ElasticMoodController.DeleteMoodEventTask deleteMoodEventTask = new
                            ElasticMoodController.DeleteMoodEventTask();
                    deleteMoodEventTask.execute(moodEvent);
                    display();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMood();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMood();
            }
        });

        return rootView;
    }


    private void display() {
        moodEvent = participant.getMostRecentMoodEvent();

        if (moodEvent != null) {
            int color = parseColor(moodEvent.getMood().getColor().getBGColor());
            this.getView().setBackgroundColor(color);
            viewmood.setText(moodEvent.getMood().toString());
            date.setText(moodEvent.getDate().toString());
            if (moodEvent.getPicture() != null) {
                imageView.setImageBitmap(moodEvent.getPicture().getImage());
            }
            if (moodEvent.getLocation() != null)
                location.setText(moodEvent.getLocation().getLatitudeStr().concat(moodEvent.getLocation().getLongitudeStr()));
        } else {
            this.getView().setBackgroundColor(Color.BLACK);
            viewmood.setText("");
            date.setText("There's No Mood Event Yet! Why Don't you add one!");
            //location.setText("");
            imageView.setImageBitmap(null);
        }
    }

    /**
     * Launch the edit mood event activity
     * passing it the uniqueID of the mood event to be edited as extras
     */
    private void editMood() {
        if (moodEvent != null) {
            Intent intent = new Intent(getActivity(), EditMoodActivity.class);
            intent.putExtra("moodEventId", moodEvent.getId());
            startActivity(intent);
        }
    }

    /**
     * Launch the view mood event activity
     * passing it the uniqueID of the mood event to be viewed as extras
     */
    private void viewMood() {
        if (moodEvent != null) {
            Intent intent = new Intent(getActivity(), ViewMoodEventActivity.class);
            intent.putExtra("moodEventId", moodEvent.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Refresh display
        display();
    }
}
