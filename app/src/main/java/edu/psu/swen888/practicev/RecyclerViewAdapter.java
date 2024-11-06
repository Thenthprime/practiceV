package edu.psu.swen888.practicev;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Event> eventsList;

    public RecyclerViewAdapter(ArrayList<Event> eventsList){
        this.eventsList = eventsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView event;
        private TextView address;

        public MyViewHolder(final View view){
            super(view);
            event = view.findViewById(R.id.textview_event_name);
            address = view.findViewById(R.id.textview_event_address);
        }
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.event.setText(eventsList.get(position).getName());
        holder.address.setText(eventsList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        if(eventsList != null) {
            return eventsList.size();
        }
        return 0;
    }
}
