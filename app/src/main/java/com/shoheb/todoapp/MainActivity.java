package com.shoheb.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.shoheb.todoapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements SelectListener{
    ActivityMainBinding binding;
    private boolean isFilterOpen;
    ArrayList<Item> items;
    ArrayList<Item> toCompleteItems,completedItems;
    DatabaseHelper databaseHelper;
    RecyclerAdapter recyclerAdapter;
    private boolean isListOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        isFilterOpen = false;
        isListOpen = true;
        actionBar.hide();
        items = new ArrayList<>();
        toCompleteItems = new ArrayList<>();
        completedItems = new ArrayList<>();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        databaseHelper = new DatabaseHelper(this);
        items = databaseHelper.getAllList();
        if(items.size()==0)return;
        for(Item item:items){
            if(item.getItem_status().equals("Completed"))
                completedItems.add(item);
            else
                toCompleteItems.add(item);
        }
        items = toCompleteItems;
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(items,this);
        binding.recyclerview.setAdapter(recyclerAdapter);

        binding.filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFilterOpen) {
                    openCloseFilters(v);
                } else {
                    openCloseFilters(v);
                }
                isFilterOpen = !isFilterOpen;
            }
        });
        binding.categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"C",Toast.LENGTH_SHORT).show();
                Collections.sort(items, new CategoryComparator());
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.priorities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"P",Toast.LENGTH_SHORT).show();
                Collections.sort(items, new PriorityComparator());
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"T",Toast.LENGTH_SHORT).show();
                Collections.sort(items, new DateTimeComparator());
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapter.setItems(completedItems);
//                Toast.makeText(MainActivity.this,"history"+items.size(),Toast.LENGTH_SHORT).show();
                binding.historyBtn.setBackgroundResource(R.drawable.active_btns);
                binding.listBtn.setBackgroundResource(R.drawable.inactive_btns);
                isListOpen = false;
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapter.setItems(toCompleteItems);
//                Toast.makeText(MainActivity.this,"list"+items.size(),Toast.LENGTH_SHORT).show();
                binding.listBtn.setBackgroundResource(R.drawable.active_btns);
                binding.historyBtn.setBackgroundResource(R.drawable.inactive_btns);
                isListOpen = true;
                recyclerAdapter.notifyDataSetChanged();
            }
        });
    }



    public void addTodo(View view) {
        Intent intent = new Intent(MainActivity.this, CreateEditActivity.class);
        startActivity(intent);
    }

    public void openCloseFilters(View view) {
        int a = isFilterOpen ? View.VISIBLE : View.GONE;
        binding.categories.setVisibility(a);
        binding.priorities.setVisibility(a);
        binding.time.setVisibility(a);
    }


    public void openAddWindow(Item item){
        TextView name,cat,priority,dueDate,desc;
        ImageView edit;

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_details,null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);
        name = popupView.findViewById(R.id.name1);
        cat = popupView.findViewById(R.id.cat1);
        priority = popupView.findViewById(R.id.pri);
        dueDate = popupView.findViewById(R.id.time1);
        desc = popupView.findViewById(R.id.desc1);
        edit = popupView.findViewById(R.id.edit);
        name.setText(item.getItem_name());
        cat.setText(item.getItem_cat());
        dueDate.setText(item.getDue_date());
        priority.setText(item.getItem_priority());
        desc.setText(item.getItem_desc());

        if(isListOpen){
            edit.setBackgroundResource(R.drawable.edit);
        }else{
            edit.setBackgroundResource(R.drawable.delete);
        }
//        changing update
        RadioGroup rg = popupView.findViewById(R.id.radioGroup);
        RadioButton rb1,rb2;
        rb1 = popupView.findViewById(R.id.toStart);
        rb2 = popupView.findViewById(R.id.inProgress);
//        Toast.makeText(MainActivity.this,item.getItem_status(),Toast.LENGTH_SHORT).show();
        if(item.getItem_status().contains("Start")){
            rb1.setChecked(true);
        }else if(item.getItem_status().equals("In-Progress")){
            rb2.setChecked(true);
        }else{
            rg.setVisibility(View.GONE);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String status = ((RadioButton)popupView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                item.setItem_status(status);
                databaseHelper.updateTask(item.getItem_id(),item);
//                Toast.makeText(MainActivity.this,"Changed to "+status,Toast.LENGTH_SHORT).show();
            }
        });
        popupView.findViewById(R.id.completed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCompleteItems.remove(item);
                item.setItem_status("Completed");
                completedItems.add(item);
                databaseHelper.updateTask(item.getItem_id(),item);
                recyclerAdapter.setItems(toCompleteItems);
                recyclerAdapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity.this,"Well done you have completed the task",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        Log.d("intent items",item.getItem_name());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isListOpen){

                    Log.d("intent items",item.getItem_name());
                    Log.d("intent items","hello");
                    Intent intent = new Intent(MainActivity.this,CreateEditActivity.class);
                    intent.putExtra("id",item.getItem_id());
                    intent.putExtra("name",item.getItem_name());
                    intent.putExtra("desc",item.getItem_desc());
                    intent.putExtra("cat",item.getItem_cat());
                    intent.putExtra("dueDate",item.getDue_date());
                    intent.putExtra("pri",item.getItem_priority());
                    startActivity(intent);

                }else{
                    completedItems.remove(item);
                    databaseHelper.deleteTask(item.getItem_id());
                    popupWindow.dismiss();
                    recyclerAdapter.setItems(completedItems);
                    recyclerAdapter.notifyDataSetChanged();
                }


            }
        });
        popupView.findViewById(R.id.backp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        binding.mainLayout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(binding.mainLayout, Gravity.BOTTOM,0,0);
            }
        });

    }

    @Override
    public void onItemClicked(Item item) {
        openAddWindow(item);
    }

}
