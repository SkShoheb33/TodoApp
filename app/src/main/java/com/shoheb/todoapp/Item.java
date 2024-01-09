package com.shoheb.todoapp;

import java.util.Date;

public class Item {
    private String item_name, item_desc;
    private String item_cat;
    private String item_priority;
    private String item_status;
    private String due_date, created_date;
    public  Item(){}
    public Item(String item_name, String item_desc, String item_cat, String item_priority, String item_status, String due_date, String created_date) {
        this.item_name = item_name;
        this.item_desc = item_desc;
        this.item_cat = item_cat;
        this.item_priority = item_priority;
        this.item_status = item_status;
        this.due_date = due_date;
        this.created_date = created_date;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public String getItem_cat() {
        return item_cat;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_priority() {
        return item_priority;
    }

    public String getItem_status() {
        return item_status;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public void setItem_cat(String item_cat) {
        this.item_cat = item_cat;
    }

    public void setItem_priority(String item_priority) {
        this.item_priority = item_priority;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
