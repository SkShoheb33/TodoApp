package com.shoheb.todoapp;
import java.util.Comparator;

public class PriorityComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        int priorityValue1 = getPriorityValue(item1.getItem_priority());
        int priorityValue2 = getPriorityValue(item2.getItem_priority());

        return Integer.compare(priorityValue1, priorityValue2);
    }
    private int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "high":
                return 3;
            case "medium":
                return 2;
            case "low":
                return 1;
            default:
                return 0;
        }
    }
}
