package model;

import java.text.DecimalFormat;

public class ProductsData {
    private final String itemName;
    private final int itemPrice;
    private final int itemQuantity;
    private final String itemPromotion;

    private ProductsData(String itemName, int itemPrice, int itemQuantity, String itemPromotion) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemPromotion = itemPromotion;
    }

    public static ProductsData of(String productsData) {
        String[] products = productsData.split(",");

        if (products[3].equals("null")) products[3] = "";

        return new ProductsData(
                products[0],
                Integer.parseInt(products[1]),
                Integer.parseInt(products[2]),
                products[3]
        );
    }

    public String getMenuData() {
        String menuPay = new DecimalFormat("#,###").format(itemPrice);
        return new StringBuilder().append("- ").append(itemName).append(" ").append(menuPay).append("원 ").append(itemQuantity)
                .append("개 ").append(itemPromotion).toString();
    }
}
