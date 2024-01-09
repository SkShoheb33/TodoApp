package com.shoheb.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.shoheb.todoapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SelectListener{
    ActivityMainBinding binding;
    private boolean isFilterOpen;
    ArrayList<Item> items;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        isFilterOpen = false;
//        items = new Item[]{new Item(), new Item(), new Item(), new Item(),new Item(), new Item(),new Item(),new Item(), new Item(), new Item(),new Item()};
        actionBar.hide();
        items = new ArrayList<>();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        databaseHelper = new DatabaseHelper(this);
        items = databaseHelper.getAllList();
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(items,this);
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
        name.setText(item.getItem_name());
        cat.setText(item.getItem_cat());
        dueDate.setText(item.getDue_date());
        priority.setText(item.getItem_priority());
        desc.setText(item.getItem_desc());
        popupView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateEditActivity.class);
                startActivity(intent);
            }
        });
        popupView.findViewById(R.id.backp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.completed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                save and dismiss
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
