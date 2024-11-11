package controller;

import com.sun.tools.javac.Main;
import data.ProductsData;
import data.PromotionsData;
import java.io.InputStream;
import java.util.List;
import services.StoreService;
import view.InputView;
import view.OutputView;

public class StoreController {
    private InputView inputView;
    private StoreService storeService;
    private OutputView outputView;

    public StoreController(StoreService storeService) {
        this.inputView = new InputView();
        this.storeService = storeService;
        this.outputView = new OutputView();
    }

    public void run() {
        List<ProductsData> productsData = getProductsData();
        List<PromotionsData> promotionsData = getPromotionsData();
    }

    private List<ProductsData> getProductsData() {
        InputStream productsLink = Main.class.getClassLoader().getResourceAsStream("products.md");
        return storeService.generateProducts(productsLink);
    }

    private List<PromotionsData> getPromotionsData() {
        InputStream promotionsLink = Main.class.getClassLoader().getResourceAsStream("promotions.md");
        return storeService.generatePromotions(promotionsLink);
    }
}
