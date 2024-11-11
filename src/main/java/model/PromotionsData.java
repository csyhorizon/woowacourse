package model;

public class PromotionsData {
    private String itemName;
    private int itemBuyCount;
    private int itemGetCount;
    private String startDate;
    private String endDate;

    private PromotionsData (String itemName, int itemBuyCount, int itemGetCount, String startDate, String endDate) {
        this.itemName = itemName;
        this.itemBuyCount = itemBuyCount;
        this.itemGetCount = itemGetCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PromotionsData of(String promotionsData) {
        String[] products = promotionsData.split(",");

        if (products[3] == null) products[3] = "0";

        return new PromotionsData(
                products[0],
                Integer.parseInt(products[1]),
                Integer.parseInt(products[2]),
                products[3],
                products[4]
        );
    }
}
