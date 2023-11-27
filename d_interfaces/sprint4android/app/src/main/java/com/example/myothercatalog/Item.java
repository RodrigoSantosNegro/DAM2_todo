package com.example.myothercatalog;

public class Item {
    private String itemName;
    private String itemDescription;
    private String itemUrl;

    public Item(String itemName, String itemDescription, String itemUrl) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemUrl = itemUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemUrl() {
        return itemUrl;
    }
}

