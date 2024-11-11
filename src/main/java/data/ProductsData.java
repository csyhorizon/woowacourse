package data;

public class ProductsData {
    private String itemName;
    private int itemPrice;
    private int itemQuantity;
    private String itemPromotion;

    private ProductsData(String itemName, int itemPrice, int itemQuantity, String itemPromotion) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemPromotion = itemPromotion;
    }

    public static ProductsData of(String productsData) {
        String[] products = productsData.split(",");

        if (products[3] == null) products[3] = "0";

        return new ProductsData(
                products[0],
                Integer.parseInt(products[1]),
                Integer.parseInt(products[2]),
                products[3]
        );
    }
}
