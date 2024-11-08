package edu.psu.swen888.practicev;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AllEventsFragment extends Fragment {

    RecyclerView mRecyclerView;
    ArrayList<Event> eventsList = new ArrayList<>();
    RecyclerViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_events, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewAllEvents);
        adapter = new RecyclerViewAdapter(eventsList);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}