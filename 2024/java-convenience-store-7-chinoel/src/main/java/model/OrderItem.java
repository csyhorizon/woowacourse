package model;

public class OrderItem {
    private String itemName;
    private int quantity;

    public OrderItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public OrderItem getItemData() {
        return this;
    }
}
