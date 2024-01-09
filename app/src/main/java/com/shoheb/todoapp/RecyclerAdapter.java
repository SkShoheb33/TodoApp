package com.shoheb.todoapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Item> localDataSet;
    private SelectListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView day;
        private final TextView month;
        private final TextView name;
        private final TextView cat;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            day = view.findViewById(R.id.day);
            month = view.findViewById(R.id.month);
            name = view.findViewById(R.id.name);
            cat = view.findViewById(R.id.cat);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    public RecyclerAdapter(ArrayList<Item> dataSet, SelectListener listener) {
        localDataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Item item = localDataSet.get(position);
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        Log.d("item", item.toString());
        String dateTime[] = item.getDue_date().toString().split("-");
        String date[] = dateTime[0].split("/");
        viewHolder.day.setText(date[0]);
        if(item.getItem_priority().equals("High")){
            viewHolder.day.setBackgroundColor(Color.parseColor("#C23F3F"));
        }else if(item.getItem_priority().equals("Medium")){
            viewHolder.day.setBackgroundColor(Color.parseColor("#BDB252"));
        }else{
            viewHolder.day.setBackgroundColor(Color.parseColor("#49B240"));
        }
        viewHolder.cat.setText(item.getItem_cat());
        viewHolder.month.setText(months.get(Integer.parseInt(date[1])));
        viewHolder.name.setText(item.getItem_name());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(localDataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
