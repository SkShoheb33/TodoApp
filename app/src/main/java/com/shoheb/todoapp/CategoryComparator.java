package com.shoheb.todoapp;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        return item1.getItem_cat().compareTo(item2.getItem_cat());
    }
}
