package com.shoheb.todoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;
//import android.widget.Toast;

import com.shoheb.todoapp.databinding.ActivityCreateEditBinding;
import com.shoheb.todoapp.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Date;

public class CreateEditActivity extends AppCompatActivity {

    String[] categories = {"Entertainment", "Studying", "Health"};
    ActivityCreateEditBinding binding;
    private DatabaseHelper databaseHelper;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> cats;
    private String date,time;
    private boolean isEdit = false;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        date = "";
        time = "";
        binding = ActivityCreateEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        if(intent.getStringExtra("name")!=null){
            id = (intent.getIntExtra("id",0));
            binding.name.setText(intent.getStringExtra("name"));
            binding.desc.setText(intent.getStringExtra("desc"));
            binding.categories.setText(intent.getStringExtra("cat"),true);
           String pri = intent.getStringExtra("pri");
            if(pri.equals("High"))
                binding.radioButton.setChecked(true);
            else if(pri.equals("Medium"))
                binding.radioButton2.setChecked(true);
            else
                binding.radioButton3.setChecked(true);
            binding.dateTime.setText(intent.getStringExtra("dueDate"));
            String[] temp = intent.getStringExtra("dueDate").split("-");
            date = temp[0];
            time = temp[1];
            isEdit = true;
        }
        databaseHelper = new DatabaseHelper(this);
        autoCompleteTextView = binding.categories;
        cats = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        autoCompleteTextView.setAdapter(cats);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
            }
        });

//        add btn click
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = findViewById(R.id.radio);
                Item item = new Item(
                        String.valueOf(binding.name.getText()),
                        String.valueOf(binding.desc.getText()),
                        String.valueOf(binding.categories.getText()),
                        ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString(),
                        "To Start",
                        String.valueOf(binding.dateTime.getText()),
                        (new Date()).toString()
                );
                if(isEdit){
                    databaseHelper.updateTask(id,item);
//                    Toast.makeText(CreateEditActivity.this,"task updated successfully",Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseHelper.insertTask(item);
//                    Toast.makeText(CreateEditActivity.this,"task Added successfully",Toast.LENGTH_SHORT).show();
                }

                databaseHelper.close();
                Intent intent = new Intent(CreateEditActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });


    }

    public void goToHome(View view) {
        Intent intent = new Intent(CreateEditActivity.this,MainActivity.class);
        startActivity(intent);
    }public void save(View view) {
        Intent intent = new Intent(CreateEditActivity.this,MainActivity.class);
        startActivity(intent);
    }


    // Open date picker dialog
    public void openDateDialog(View v) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int todayYear = calendar.get(Calendar.YEAR);
        int todayMonth = calendar.get(Calendar.MONTH) + 1;
        int toDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme,(view, year, month, dayOfMonth) -> {
            date = getDay(dayOfMonth,month,year);
            binding.dateTime.setText(date+"-"+time);
        }, todayYear, todayMonth, toDay);
        dialog.show();
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFFFFF"));
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#F89E4A"));
    }

    public void openTimeDialog(View v){
        TimePickerDialog dialog = new TimePickerDialog(this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = getTime(hourOfDay,minute);
                binding.dateTime.setText(date+"-"+time);
            }
        }, 12, 0, false);
        dialog.show();
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFFFFF"));
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#F89E4A"));
    }
    public String getTime(int hourOfDay,int minute){
        String result;
        if (hourOfDay < 10) {
            result = "0" + hourOfDay + ":";
        } else {
            result = hourOfDay + ":";
        }

        if (minute < 10) {
            result += "0" + minute;
        } else {
            result += minute;
        }

        if (hourOfDay < 12) {
            result += " AM";
        } else {
            result += " PM";
        }
        return  result;
    }
    public String getDay(int dayOfMonth,int month,int year){
        String result;
        if (dayOfMonth < 10) {
            result = "0" + dayOfMonth + "/";
        } else {
            result = dayOfMonth + "/";
        }
        if (month + 1 < 10) {
            result += "0" + (month + 1) + "/";
        } else {
            result += (month + 1) + "/";
        }
        result += year;
        return result;
    }
}
