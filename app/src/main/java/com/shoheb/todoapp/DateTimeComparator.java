package com.shoheb.todoapp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class DateTimeComparator implements Comparator<Item> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-hh:mm a", Locale.US);

    @Override
    public int compare(Item item1, Item item2) {
        try {
            Date date1 = dateFormat.parse(item1.getDue_date());
            Date date2 = dateFormat.parse(item2.getDue_date());

            // Compare the parsed dates
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
