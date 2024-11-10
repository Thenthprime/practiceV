package edu.psu.swen888.practicev;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddEventFragment extends Fragment {

Button button_add_event;
EditText et_event_name, et_event_location;
Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        //find user fields by layout id
        button_add_event= view.findViewById(R.id.button_add_event);
        et_event_name = view.findViewById(R.id.et_event_name);
        et_event_location = view.findViewById(R.id.et_event_location);

        //button listeners
        button_add_event.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //convert text input to string
                String stringPath = et_event_name.getText().toString();
                //get resource package associated with string
                String addressPath = et_event_location.getText().toString();
                Event event = new Event(-1, stringPath, addressPath);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.addOne(event);
                ViewGroup group = (ViewGroup)view.findViewById(R.id.add_event_fragment);
                Toast.makeText(getContext(), "Event Added to the Database.", Toast.LENGTH_SHORT).show();
                //clear the fields
                for (int i = 0, count = group.getChildCount(); i < count; ++i) {
                    View view = group.getChildAt(i);
                    if (view instanceof EditText) {
                        ((EditText)view).setText("");
                    }
                }
            }
        });




        // Inflate the layout for this fragment
        return view;
    }
}